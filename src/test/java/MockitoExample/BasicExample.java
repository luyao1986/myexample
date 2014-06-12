package MockitoExample;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by shiyao on 5/4/14.
 */
public class BasicExample {
    List mockedList = mock(List.class);

    @Test
    public void test1() throws Exception {

        mockedList.add("one");
        mockedList.add("one");
        mockedList.clear();
        mockedList.add("3");
        verify(mockedList, times(2)).add("one");
        verify(mockedList).clear();
        verify(mockedList, never()).add("2");           //verify the behavior
        verify(mockedList, atMost(5)).add("3");

        when(mockedList.get(0)).thenReturn("first");    //we can use "when" to do stub
        System.out.println(mockedList.get(0));
        when(mockedList.get(anyInt())).thenReturn("anyone");
        System.out.println(mockedList.get(0));

        when(mockedList.get(anyInt())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                return "called with arguments: " + args.toString();
            }
        });
        System.out.println(mockedList.get(50));         //stub by arguments
    }

    @Test(expected = RuntimeException.class)
    public void test2() throws Exception {
        when(mockedList.get(1)).thenReturn("i am one").
                thenThrow(new RuntimeException());
        System.out.println(mockedList.get(100));    //创建一个类的mock实例时，所有的方法都会有基本行为, for example this will return null
        System.out.println(mockedList.get(1));
        System.out.println(mockedList.get(1));
    }

    @Test
    public void test3() throws Exception {          //verify the execute order
        //test order of 2 methods
        mockedList.add("first");
        mockedList.add("second");
        InOrder inOrder = inOrder(mockedList);
        inOrder.verify(mockedList).add("first");
        inOrder.verify(mockedList).add("second");

        //test order of 2 instance
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);
        firstMock.add("was called first");
        secondMock.add("was called second");
        inOrder = inOrder(firstMock, secondMock);
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");
    }

    @Test
    public void test4() throws Exception {
        List list = new LinkedList();
        List spy = spy(list);

        when(spy.size()).thenReturn(100);           //修改某个真实对象的某些方法的行为特征，而不改变他的基本行为特征 by spy
        spy.add("one");
        spy.add("two");
        verify(spy).add("one");
        verify(spy).add("two");
        System.out.println(spy.get(0));
        System.out.println(spy.size());

        when(spy.get(1)).thenReturn("hello");
        System.out.println(spy.get(1));
        reset(spy);
        System.out.println(spy.size());

//        Assert.assertEquals(Mockito.mockingDetails(spy).isMock(), false);

    }
}
