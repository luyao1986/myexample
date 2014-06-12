package leetcode;

import java.util.List;

/**
 * Created by shiyao on 5/15/14.
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }
}
public class linklistInsertionsort {
    public static ListNode insertionSortList(ListNode head) {
        if(head == null) {
            return null;
        }
        else {
            ListNode pre = head;
            ListNode cur = head.next;
            if(cur == null) {
                return head;
            }
            else {
                while(cur != null) {
                    if(cur.val >= pre.val) {
                        pre = cur;
                        cur = cur.next;
                        continue;
                    }
                    ListNode tmp = cur.next;
                    if(cur.val < head.val) {
                        pre.next = tmp;
                        cur.next = head;
                        head = cur;
                    }
                    else {
                        ListNode tmp1 = head;
                        ListNode tmp2 = head.next;
                        while(tmp2.val < cur.val ) {
                            tmp1 = tmp1.next;
                            tmp2 = tmp2.next;
                        }
                        //now put it afte tmp1, before tmp2
                        pre.next = tmp;
                        tmp1.next = cur;
                        cur.next = tmp2;
                    }
                    cur = tmp;
                }
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode pre = null;
        for (int i = 0; i < 2; i++) {
            ListNode cur = new ListNode(1);
            cur.next = pre;
            pre = cur;
        }
        printListNode(pre);
        printListNode(insertionSortList(pre));
    }

    public static void printListNode(ListNode pre) {
        while(pre  != null) {
            System.out.print("->" + pre.val);
            pre = pre.next;
        }
        System.out.println();
    }
}
