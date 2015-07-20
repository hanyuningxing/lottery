package com.cai310.lottery.support;

import java.util.Arrays;

/**
 * 小复式拆单式
 * @author jack
 *
 */
public class Compound2Single {

	//使用二维数组参数方法返回组合的二维数组类型，并使用了递归
	public static Object[][] assembleArraysToPlanerArray(Object[][] objectArrays) {
		if (objectArrays.length == 2) {
			Object[] assembledArray = objectArrays[0];
			Object[] array = objectArrays[1];
			return assembleArrayToArray(assembledArray, array);
		} else if (objectArrays.length <= 1) {
			return objectArrays;
		} else {
			Object[] objArray = objectArrays[objectArrays.length - 1];
			objectArrays = Arrays.copyOf(objectArrays, objectArrays.length - 1);
			return assembleArrayToArray(
					assembleArraysToPlanerArray(objectArrays), objArray);
		}
	}

	 //将一个数组类型或二维数组类型与数组组合，并返回二维数组
	public static Object[][] assembleArrayToArray(Object[] assembledArray,
			Object[] array) {
		int lenAssArray = assembledArray.length;
		int lenArray = array.length;
		Object[][] objArrays = new Object[lenAssArray * lenArray][];
		for (int i = 0; i < lenAssArray; i++) {
			Object obj = assembledArray[i];
			if (obj instanceof Object[]) {
				Object[] objArr = (Object[]) obj;
				int lenObjArr = objArr.length;
				for (int k = 0; k < lenArray; k++) {
					// 复制objArr数组到newListArr数组，并将其长度加一
					Object[] newListArr = Arrays.copyOf(objArr, lenObjArr + 1);
					// 将array数组的第k+1元素赋值给newListArr数组最后的元素,并将newListArr赋值给objArrays数组的第k+1个元素
					newListArr[lenObjArr] = array[k];
					objArrays[lenArray * i + k] = newListArr;
				}
			} else {
				for (int j = 0; j < lenArray; j++) {
					Object[] newObjArray = { obj, array[j] };
					objArrays[lenArray * i + j] = newObjArray;
				}
			}
		}
		return objArrays;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer[] arrOfInt = { 1 };
		Character[] arrOfChar1 = { 'a' };
		Character[] arrOfChar2 = { '!' };
		String[] arrOfStr = { "lmy", "lyb", "mz", "yx" };

		Object[][] items = new Object[4][];
		items[0] = arrOfInt;
		items[1] = arrOfChar1;
		items[2] = arrOfChar2;
		items[3] = arrOfStr;
		Object[][] objectsArrays = assembleArraysToPlanerArray(items);
		System.out.println(objectsArrays.length);
		for (Object[] objArrays : objectsArrays) {
			System.out.println(Arrays.toString(objArrays));
		}

	}

}
