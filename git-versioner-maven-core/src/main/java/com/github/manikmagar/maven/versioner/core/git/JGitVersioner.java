/* (C)2022 */
package com.github.manikmagar.maven.versioner.core.git;

import com.github.manikmagar.maven.versioner.core.params.VersionConfig;
import com.github.manikmagar.maven.versioner.core.version.*;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class JGitVersioner implements Versioner {

	VersionConfig versionConfig;

	public JGitVersioner(VersionConfig versionConfig) {
		this.versionConfig = versionConfig;
	}

	public VersionStrategy version() {
		return JGit.executeOperation(git -> {
			var branch = git.getRepository().getBranch();
			Ref head = git.getRepository().findRef("HEAD");
			var hash = "";
			if (head != null && head.getObjectId() != null) {
				hash = head.getObjectId().getName();
			}
			var versionStrategy = new VersionPatternStrategy(versionConfig.getInitial().getMajor(),
					versionConfig.getInitial().getMinor(), versionConfig.getInitial().getPatch(), branch, hash,
					versionConfig.getVersionPattern().getPattern());
			var commits = git.log().call();
			List<RevCommit> revCommits = StreamSupport.stream(commits.spliterator(), false)
					.collect(Collectors.toList());
			Collections.reverse(revCommits);
			for (RevCommit commit : revCommits) {
				if (hasValue(commit.getFullMessage(), versionConfig.getKeywords().getMajorKey())) {
					versionStrategy.increment(VersionComponentType.MAJOR, hash);
				} else if (hasValue(commit.getFullMessage(), versionConfig.getKeywords().getMinorKey())) {
					versionStrategy.increment(VersionComponentType.MINOR, hash);
				} else if (hasValue(commit.getFullMessage(),versionConfig.getKeywords().getPatchKey())) {
					versionStrategy.increment(VersionComponentType.PATCH, hash);
				} else {
					versionStrategy.increment(VersionComponentType.COMMIT, hash);
				}
			}
			return versionStrategy;
		});
	}

	boolean hasValue(String commitMessage, String keyword) {
		if(versionConfig.getKeywords().isUseRegex()){
			return commitMessage.matches(keyword);
		}else {
			return commitMessage.contains(keyword);
		}
	}
}
