package exercise;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

// BEGIN
public class PairedTag extends Tag {

    private String tagBody;
    private List<Tag> child;

    public PairedTag(String tagName, Map<String, String> tagAttributes, String tagBody, List<Tag> child) {
        super(tagName, tagAttributes);
        this.tagBody = tagBody;
        this.child = child;
    }

    @Override
    public String toString() {
        return ("<" + this.getTagName() + " " + this.attributesToString()).trim() + ">"
                + this.tagBody + this.childToString()
                + "</" + this.getTagName() + ">";
    }

    private String childToString() {
        return this.child.stream()
                .map((tag) -> tag.toString())
                .collect(Collectors.joining());
    }
}
// END
