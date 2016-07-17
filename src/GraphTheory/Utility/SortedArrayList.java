/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphTheory.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Thayer
 */
public class SortedArrayList<T>{
    Comparator c;
    ArrayList<T> arr;

    public SortedArrayList(Comparator<T> comp){
        super();
        c = comp;
        arr = new ArrayList<>();
    }

    public void add(T value){
        arr.add(value);
        for (int i = arr.size()-1; i > 0 && c.compare(value,arr.get(i-1)) < 0; i--)
            Collections.swap(arr, i, i-1);
    }
    
    public void addAll(Collection<? extends T> values){
        for(T v:values)
            add(v);
    }

    public void remove(int index){
        arr.remove(index);
    }

    public void remove(T value){
        int index = binarySearch(value);
        if(index != -1)
            remove(index);
    }

    public int indexOf(T value){
        return binarySearch(value);
    }

    private int binarySearch(T value){
        int low = 0;
        int high = arr.size()-1;
        int mid = (low+high)/2;
        int cmp_val;
        while(high>=low){
            cmp_val = c.compare(value, arr.get(mid));
            if(cmp_val < 0)
                high=mid-1;
            else if(cmp_val > 0)
                low=mid+1;
            else{
                if(value == arr.get(mid))
                    return mid;
                else
                    return subsearch(value, mid);//allow for multiple objects with same compared attribute
            }
            mid = (low+high)/2;
        }
        return -1;//fail to find
    }

    private int subsearch(T value, int index){
        int position = index-1;
        while(c.compare(value, arr.get(position))==0)//look left from the first index where we saw a matching attribute
            if(value==arr.get(position))
                return position;
            else
                position--;

        position = index+1;
        while(c.compare(value, arr.get(position))==0)
            if(value==arr.get(position))
                return position;
            else
                position++;

        return -1;//fail to find
    }
}
