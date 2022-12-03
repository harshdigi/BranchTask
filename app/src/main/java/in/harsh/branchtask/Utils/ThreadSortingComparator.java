package in.harsh.branchtask.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import in.harsh.branchtask.Models.MessageThreadModel;

public class ThreadSortingComparator implements Comparator<Integer> {
    HashMap<Integer, ArrayList<MessageThreadModel>> messagesMap;

    public ThreadSortingComparator(HashMap<Integer, ArrayList<MessageThreadModel>> messagesMap) {
        this.messagesMap = messagesMap;
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return messagesMap.get(o1).get(0).getTimestamp().compareTo(messagesMap.get(o2).get(0).getTimestamp());
    }
}
