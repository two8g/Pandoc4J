package galvin.pandoc;

/**
 * list Non-pandoc extensions.
 * http://pandoc.org/README.html#non-pandoc-extensions
 * Created by two8g on 16-4-25.
 */
public enum Extension {

	tex_math_dollars("tex_math_dollars", Format.html),
	tex_math_single_backslash("tex_math_single_backslash", Format.html),
	tex_math_double_backslash("tex_math_double_backslash", Format.html),
	auto_identifiers("auto_identifiers",Format.latex);

	private String extension;
	private Format format;

	private Extension(String extension, Format format) {
		this.extension = extension;
		this.format = format;
	}

	public String extension() {
		return extension;
	}

	public Format format() {
		return format;
	}
}
