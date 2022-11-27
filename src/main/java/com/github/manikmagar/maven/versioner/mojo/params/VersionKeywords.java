/* (C)2022 */
package com.github.manikmagar.maven.versioner.mojo.params;

import org.apache.maven.plugins.annotations.Parameter;

import java.util.Objects;

/**
 * Define the version keywords to use when parsing git commit messages.
 */
public final class VersionKeywords {
	public static final String KEY_MAJOR = "[major]";
	public static final String KEY_MINOR = "[minor]";
	public static final String KEY_PATCH = "[patch]";

	/**
	 * The keyword for calculating major version of the SemVer.
	 */
	@Parameter(name = "majorKey", defaultValue = KEY_MAJOR)
	private String _majorKey = KEY_MAJOR;
	/**
	 * The keyword for calculating minor version of the SemVer.
	 */
	@Parameter(name = "minorKey", defaultValue = KEY_MINOR)
	private String _minorKey = KEY_MINOR;
	/**
	 * The keyword for calculating patch version of the SemVer.
	 */
	@Parameter(name = "patchKey", defaultValue = KEY_PATCH)
	private String _patchKey = KEY_PATCH;

	public String getMajorKey() {
		return _majorKey;
	}

	public void setMajorKey(String majorKey) {
		if (majorKey == null || majorKey.trim().isEmpty()) {
			this._majorKey = KEY_MAJOR;
		} else {
			this._majorKey = majorKey;
		}
	}

	public String getMinorKey() {
		return _minorKey;
	}

	public void setMinorKey(String minorKey) {
		if (minorKey == null || minorKey.trim().isEmpty()) {
			this._minorKey = KEY_MINOR;
		} else {
			this._minorKey = minorKey;
		}
	}

	public String getPatchKey() {
		return _patchKey;
	}

	public void setPatchKey(String patchKey) {
		if (patchKey == null || patchKey.trim().isEmpty()) {
			this._patchKey = KEY_PATCH;
		} else {
			this._patchKey = patchKey;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof VersionKeywords))
			return false;
		VersionKeywords that = (VersionKeywords) o;
		return _majorKey.equals(that._majorKey) && _minorKey.equals(that._minorKey) && _patchKey.equals(that._patchKey);
	}

	@Override
	public int hashCode() {
		return Objects.hash(_majorKey, _minorKey, _patchKey);
	}
}
