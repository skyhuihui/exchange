package com.zag.support.jpa.converter;

/*
 * List<Integer> -> "1,2,3,4"
 * String(e.g.  "1,2,3,4"  ) -> List<Integer>
 *
 */
public class ListIntegerConverter extends AbstractListStringConverter<Integer> {

    @Override
    protected Integer string2Element(String data) {
        return Integer.valueOf(data);
    }

}
