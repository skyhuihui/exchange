package com.zag.support.jpa.converter;

/*
 * List<Long> -> "1,2,3,4"
 * String(e.g.  "1,2,3,4"  ) -> List<Long>
 *
 */
public class ListLongConverter extends AbstractListStringConverter<Long> {
    public static final ListLongConverter INSTANCE = new ListLongConverter();
    private static final long serialVersionUID = -858560178317910071L;

    @Override
    protected Long string2Element(String data) {
        return Long.valueOf(data);
    }

}
