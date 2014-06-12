package leetcode;

/**
 * Created by shiyao on 5/15/14.
 */
public class AddTwoBigInt {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int c = 0;
        ListNode tail = null;
        ListNode head = null;
        while(l1!=null || l2!=null) {
            int sum = c;
            sum += (l1==null)?0:l1.val;
            sum += (l2==null)?0:l2.val;
            c = sum/10;
            sum %= 10;
            if(l1!=null)
                l1 = l1.next;
            if(l2!=null)
                l2 = l2.next;
            ListNode cur = new ListNode(sum);
            if(head == null) {
                head = cur;
                tail = head;
            }
            else {
                tail.next = cur;
                tail = cur;
            }
        }
        if(c==1) {
            ListNode cur = new ListNode(1);
            tail.next = cur;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode pre = null;
        for (int i = 5; i < 6; i++) {
            ListNode cur = new ListNode(i);
            cur.next = pre;
            pre = cur;
        }
        ListNode pre2 = null;
        for (int i = 5; i < 6; i++) {
            ListNode cur = new ListNode(i);
            cur.next = pre2;
            pre2 = cur;
        }
        ListNode result = addTwoNumbers(pre, pre2);
        linklistInsertionsort.printListNode(result);
    }
}

