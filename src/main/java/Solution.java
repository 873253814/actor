import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Solution {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();

    ExecutorService executorService = Executors.newFixedThreadPool(8);

    public ReentrantReadWriteLock.ReadLock getReadLock() {
       return lock.readLock();
    }

    public ReentrantReadWriteLock.WriteLock getWriteLock() {
        return lock.writeLock();
    }

    private int count = 5;

    public void read() {
        getReadLock().lock();
        try {
            count++;
            System.out.println(Thread.currentThread()+ "count:" + count);
        } finally {
            getReadLock().unlock();
        }
    }

    public void count() {
        count++;
        System.out.println(Thread.currentThread()+ "count:" + count);
    }

    public static void main(String[] args) throws InterruptedException {
        Solution solution = new Solution();
        Thread threadA = new Thread(new Runnable() {
            public void run() {
                solution.lock.writeLock().lock();
                try {
                    solution.count();
                } finally {
                    solution.lock.writeLock().unlock();
                }

            }
        });
        Thread threadB = new Thread(new Runnable() {
            public void run() {
                solution.lock.writeLock().lock();
                try {
                    solution.count();
                } finally {
                    solution.lock.writeLock().unlock();
                }

            }
        });
        Thread threadC = new Thread(new Runnable() {
            public void run() {
                solution.lock.writeLock().lock();
                try {
                    solution.count();
                } finally {
                    solution.lock.writeLock().unlock();
                }

            }
        });
        Thread threadD = new Thread(new Runnable() {
            public void run() {
                solution.lock.writeLock().lock();
                try {
                    solution.count();
                } finally {
                    solution.lock.writeLock().unlock();
                }

            }
        });
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }


//        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//            ListNode head = null, tail = null;
//            int carry = 0;
//            while (l1 != null || l2 != null) {
//                int n1 = l1 != null ? l1.val : 0;
//                int n2 = l2 != null ? l2.val : 0;
//                int sum = n1 + n2 + carry;
//                if (head == null) {
//                    head = tail = new ListNode(sum % 10);
//                } else {
//                    tail.next = new ListNode(sum % 10);
//                    tail = tail.next;
//                }
//                carry = sum / 10;
//                if (l1 != null) {
//                    l1 = l1.next;
//                }
//                if (l2 != null) {
//                    l2 = l2.next;
//                }
//            }
//            if (carry > 0) {
//                tail.next = new ListNode(carry);
//            }
//            return head;
//        }
//
//        public static void main(String[] args) {
//            Solution solution = new Solution();
//            ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
//            ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
//            ListNode l3 = solution.addTwoNumbers(l1, l2);
//        }
    }

