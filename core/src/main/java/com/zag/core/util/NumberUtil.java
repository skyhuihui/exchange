package com.zag.core.util;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

public class NumberUtil {
	
	private static final String[] NUM_C = {"一","二","两","三","四","五","六","七","八","九","酒"};
	
	private static final int[] NUM_A = {1,2,2,3,4,5,6,7,8,9,9};
	
	private static final String NUM_UNIT = "十";
	
	private static final int DEFAULT_MIN_NUMBER = 1;
	
	public static int chineseToNumber(String str){
		//十位
		String decade = null;
		int decade_num = 0;
		//个位
		String unit = null;
		int unit_num = 0;
		
		if(!Strings.isNullOrEmpty(str)){
			if(NUM_UNIT.equals(str)){
				return 10;
			}
			if(str.contains(NUM_UNIT)){
				if(NUM_UNIT.equals(str)){
					return 10;
				}
				decade = str.substring(0, 1);
				if(NUM_UNIT.equals(decade)){
					decade_num = 1;
					unit = str.substring(1, 2);
					for (int i = 0; i < NUM_C.length; i++) {	
						if(NUM_C[i].equals(unit)){
							unit_num = NUM_A[i];
						}
					}
					return decade_num * 10 + unit_num;
				}
				try {
					unit = str.substring(2, 3);
				} catch (Exception e) {
					unit = null;
				}
				
				for (int i = 0; i < NUM_C.length; i++) {
					if(decade.equals(NUM_C[i])){
						decade_num = NUM_A[i];
					}
					if(NUM_C[i].equals(unit)){
						unit_num = NUM_A[i];
					}
				}
				return decade_num * 10 + unit_num;
			}else {
				unit = str;
				for (int i = 0; i < NUM_C.length; i++) {
					if(NUM_C[i].equals(unit)){
						unit_num = NUM_A[i];
						break;
					}else {
						unit_num = DEFAULT_MIN_NUMBER;
					}
				}
				return unit_num;
			}
		}else {
			unit_num = DEFAULT_MIN_NUMBER;
			
			return unit_num;
		}
	}
	
	
	/**
	 * 价格 分 大于0时  向上进位
	 * @author stone
	 * @date 2017年4月25日
	 * @param price
	 * @return
	 */
	public static Integer priceOfCentUp(Integer finalPrice){
        if(finalPrice != null){
        	String price = String.valueOf(finalPrice);
        	Integer num_1 = 0;
            if(StringUtils.isNotBlank(price.substring(0, price.length()-1))){
            	num_1 = Integer.valueOf(price.substring(0, price.length()-1));
            }
            Integer num_2 = 0;
            if(StringUtils.isNotBlank(price.substring(price.length()-1))){
            	num_2 = Integer.valueOf(price.substring(price.length()-1));
            }
            if(price.length() == 1){
            	if(num_2 > 0){
                	num_2 = 0;
                	num_1 = 1;
                }
            }else {
            	if(num_2 > 0){
            		num_2 = 0;
            		num_1++;
            	}
            }
            
            
            StringBuilder strPrice = new StringBuilder();
            strPrice.append(String.valueOf(num_1)).append(Integer.valueOf(num_2));
            finalPrice = Integer.valueOf(strPrice.toString());
    		return finalPrice;
        }else {
        	return 0;
        }
	}
	
}
