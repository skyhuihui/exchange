package com.zag.support.jpa.converter;

/*
 * Set<Integer> -> "1,2,3,4"
 * String(e.g.  "1,2,3,4"  ) -> Set<Integer>
 *
 */
public class SetIntegerConverter extends AbstractSetStringConverter<Integer> {

    @Override
    protected Integer string2Element(String data) {
        return Integer.valueOf(data);
    }

}
