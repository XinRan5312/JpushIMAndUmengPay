package com.engloryintertech.small.global.utils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;

/**
 * Created by qixinh on 16/9/19.
 */
@SuppressWarnings({"unchecked"})
public class JMArrayAndCollectionUtile {
    /**
     * 加强版的asList会检查传入是否为null，以保证不会抛出异常 并且返回的arraylist改为{@link ArrayList}
     *
     * @param <T>
     * @param array
     * @return
     */
    public static <T> ArrayList<T> asList(T... array) {
        if (array == null) {
            return new ArrayList<T>(0);
        }
        ArrayList<T> list = new ArrayList<T>(Arrays.asList(array));
        return list;
    }

    public static <T> List<T> asReadOnlyList(T... array) {
        return new ReadOnlyArrayList<T>(array);
    }

    public static boolean contains(Object[] array, Object value) {
        if (array == null) {
            return false;
        }
        for (Object object : array) {
            if (object == null) {
                if (value == null) {
                    return true;
                }
            } else if (object.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在已排序数组中查询是否存在某值
     *
     * @param array
     * @param value
     * @return
     */
    public static boolean contains(int[] array, int value) {
        return Arrays.binarySearch(array, value) >= 0 ? true : false;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with <tt>false</tt> (if necessary) so the copy has the
     * specified length. For all indices that are valid in both the original array and the copy, the two arrays will
     * contain identical values. For any indices that are valid in the copy but not the original, the copy will contain
     * <tt>false</tt>. Such indices will exist if and only if the specified length is greater than that of the original
     * array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with false elements to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static boolean[] copyOf(boolean[] original, int newLength) {
        boolean[] copy = new boolean[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with zeros (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical
     * values. For any indices that are valid in the copy but not the original, the copy will contain <tt>(byte)0</tt>.
     * Such indices will exist if and only if the specified length is greater than that of the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with zeros to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with null characters (if necessary) so the copy has the
     * specified length. For all indices that are valid in both the original array and the copy, the two arrays will
     * contain identical values. For any indices that are valid in the copy but not the original, the copy will contain
     * <tt>'\\u000'</tt>. Such indices will exist if and only if the specified length is greater than that of the
     * original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with null characters to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with zeros (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical
     * values. For any indices that are valid in the copy but not the original, the copy will contain <tt>0d</tt>. Such
     * indices will exist if and only if the specified length is greater than that of the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with zeros to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static double[] copyOf(double[] original, int newLength) {
        double[] copy = new double[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with zeros (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical
     * values. For any indices that are valid in the copy but not the original, the copy will contain <tt>0f</tt>. Such
     * indices will exist if and only if the specified length is greater than that of the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with zeros to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static float[] copyOf(float[] original, int newLength) {
        float[] copy = new float[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with zeros (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical
     * values. For any indices that are valid in the copy but not the original, the copy will contain <tt>0</tt>. Such
     * indices will exist if and only if the specified length is greater than that of the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with zeros to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static int[] copyOf(int[] original, int newLength) {
        int[] copy = new int[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with zeros (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical
     * values. For any indices that are valid in the copy but not the original, the copy will contain <tt>0L</tt>. Such
     * indices will exist if and only if the specified length is greater than that of the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with zeros to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static long[] copyOf(long[] original, int newLength) {
        long[] copy = new long[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with zeros (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical
     * values. For any indices that are valid in the copy but not the original, the copy will contain <tt>(short)0</tt>.
     * Such indices will exist if and only if the specified length is greater than that of the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with zeros to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static short[] copyOf(short[] original, int newLength) {
        short[] copy = new short[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with nulls (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical
     * values. For any indices that are valid in the copy but not the original, the copy will contain <tt>null</tt>.
     * Such indices will exist if and only if the specified length is greater than that of the original array. The
     * resulting array is of exactly the same class as the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with nulls to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     */
    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }

    /**
     * 从jdk1.6拷贝过来，android中没有这些方法。<br />
     * Copies the specified array, truncating or padding with nulls (if necessary) so the copy has the specified length.
     * For all indices that are valid in both the original array and the copy, the two arrays will contain identical
     * values. For any indices that are valid in the copy but not the original, the copy will contain <tt>null</tt>.
     * Such indices will exist if and only if the specified length is greater than that of the original array. The
     * resulting array is of the class <tt>newType</tt>.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @param newType the class of the copy to be returned
     * @return a copy of the original array, truncated or padded with nulls to obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     * @throws ArrayStoreException if an element copied from <tt>original</tt> is not of a runtime type that can be
     *             stored in an array of class <tt>newType</tt>
     */
    public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        T[] copy = (Object) newType == (Object) Object[].class ? (T[]) new Object[newLength] : (T[]) Array.newInstance(
                newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    /**
     * 在数组中查询某值所在位置
     *
     * @param array
     * @param value
     * @return
     */
    public static int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        throw new ArrayIndexOutOfBoundsException(value + "is not in " + Arrays.toString(array));
    }

    /**
     * 在数组中查询某值所在位置
     *
     * @param <T>
     * @param array
     * @param value
     * @return
     */
    public static <T> int indexOf(T[] array, T value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        throw new ArrayIndexOutOfBoundsException(value.toString() + "is not in " + Arrays.toString(array));
    }

    private static class ReadOnlyArrayList<E> extends AbstractList<E> implements List<E>, Serializable, RandomAccess {

        private static final long serialVersionUID = 1L;
        private final E[] a;

        ReadOnlyArrayList(E[] storage) {
            a = storage;
        }

        @Override
        public boolean contains(Object object) {
            if (a == null) {
                return false;
            }
            if (object != null) {
                for (E element : a) {
                    if (object.equals(element)) {
                        return true;
                    }
                }
            } else {
                for (E element : a) {
                    if (element == null) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public E get(int location) {
            try {
                return a[location];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            } catch (NullPointerException e) {
                throw new IndexOutOfBoundsException();
            }
        }

        @Override
        public int indexOf(Object object) {
            if (a == null) {
                return -1;
            }
            if (object != null) {
                for (int i = 0; i < a.length; i++) {
                    if (object.equals(a[i])) {
                        return i;
                    }
                }
            } else {
                for (int i = 0; i < a.length; i++) {
                    if (a[i] == null) {
                        return i;
                    }
                }
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object object) {
            if (a == null) {
                return -1;
            }
            if (object != null) {
                for (int i = a.length - 1; i >= 0; i--) {
                    if (object.equals(a[i])) {
                        return i;
                    }
                }
            } else {
                for (int i = a.length - 1; i >= 0; i--) {
                    if (a[i] == null) {
                        return i;
                    }
                }
            }
            return -1;
        }

        @Override
        public E set(int location, E object) {
            if (a == null) {
                throw new IndexOutOfBoundsException();
            }
            try {
                E result = a[location];
                a[location] = object;
                return result;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            } catch (ArrayStoreException e) {
                throw new ClassCastException();
            }
        }

        @Override
        public int size() {
            return a == null ? 0 : a.length;
        }

        @Override
        public Object[] toArray() {
            if (a == null) {
                return new Object[0];
            }
            return a.clone();
        }

        @Override
        public <T> T[] toArray(T[] contents) {
            if (a == null) {
                return contents;
            }
            int size = size();
            if (size > contents.length) {
                Class<?> ct = contents.getClass().getComponentType();
                contents = (T[]) Array.newInstance(ct, size);
            }
            System.arraycopy(a, 0, contents, 0, size);
            if (size < contents.length) {
                contents[size] = null;
            }
            return contents;
        }
    }

    /**
     * @param array
     * @return
     */
    public static <T> boolean isEmpty(Collection<T> array) {
        if (array == null || array.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * @param array
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 合并2个array
     *
     * @param head
     * @param tail
     * @return
     */
    public static <T> T[] join(T[] head, T[] tail) {
        if (head == null) {
            return tail;
        }
        if (tail == null) {
            return head;
        }
        Class<?> type = head.getClass().getComponentType();
        T[] result = (T[]) Array.newInstance(type, head.length + tail.length);

        System.arraycopy(head, 0, result, 0, head.length);
        System.arraycopy(tail, 0, result, head.length, tail.length);

        return result;
    }
}
