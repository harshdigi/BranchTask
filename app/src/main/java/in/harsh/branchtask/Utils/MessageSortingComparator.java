package in.harsh.branchtask.Utils;

import java.util.Comparator;

import in.harsh.branchtask.Models.MessageThreadModel;

public class MessageSortingComparator implements Comparator<MessageThreadModel> {
    @Override
    public int compare(MessageThreadModel o1, MessageThreadModel o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
