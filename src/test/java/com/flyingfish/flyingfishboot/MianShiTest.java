package com.flyingfish.flyingfishboot;

import ch.qos.logback.core.pattern.ConverterUtil;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.sql.Array;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jianping.yu@karakal.com.cn
 * @version 1.0
 * @date 2023/5/29
 **/
public class MianShiTest {

    public static void main(String[] args) {
//         testOne(3558, 0,500);
//         //多开一个线程异步执行速度加快近一倍
//         new Thread(new Runnable() {
//             @Override
//             public void run() {
//                 testOne(22222222, 3558, 500);
//             }
//         }).start();



        /****************************/
//            int[][] a = {{2, 4, 6, 8, 7, 9},
//                     {3, 8, 9, 1 ,4 ,5},
//                     {6, 9, 12, 2, 15, 11},
//                     {8, 7, 18, 7, 13, 2},
//                     {7, 2, 10, 4, 3, 7},
//                     {9, 5, 6, 17, 5, 1}};
//
//        System.out.println(minPathSum(a));

//        Integer[][] a = {
//                {1,1,1,10,9},
//                {8,9,1,10,9},
//                {9,1,1,10,9},
//                {9,1,9,9,9},
//                {9,1,1,1,1}};
//
//        minimumPlannedPath( a);

        ListNode listNode = removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, null))))), 2);
        while (true) {
            if (listNode != null) {
                System.out.print(listNode.val + " ");
                listNode = listNode.next;
            } else {
                break;
            }
        }

    }

    public static void testOne(int n, int start, int num){
        int count = 0;
        for (int i = start; i <= n; i++) {
            boolean flag = true;
            long startTime = 0l;
            long endTime = 0l;
            for (int j = 2; j < i; j++) {
                startTime = System.currentTimeMillis();
                if (i%j==0) {
                    flag=false;
                    break;
                }
                endTime = System.currentTimeMillis();
            }
            if (flag) {
                System.out.print("数值：" + i+"==线程id：" + Thread.currentThread().getId() + " " + "==耗时：" + String.valueOf(endTime - startTime));
                count++;
//                if (count%8==0) {
                    System.out.println();
//                }
            }
            if (count==num) break;
        }


    }


    /**
     * 动态规划，只做统计
     *
     * @param integer 数组
     */
    private static void minimumPlannedPath(Integer[][] integer) {
        Integer row = integer.length;
        Integer column = integer[0].length;
        Integer[][] min = new Integer[row][column];
        // 起点值
        min[0][0] = integer[0][0];
        // 第一列
        for (int r = 1; r < row; r++)
            min[r][0] = integer[r][0] + min[r - 1][0];
        // 第一行
        for (int c = 1; c < column; c++)
            min[0][c] = integer[0][c] + min[0][c - 1];
        // 别的值
        for (int r = 1; r < row; r++) {
            for (int c = 1; c < column; c++) {
                int minValue = Math.min(min[r][c - 1], min[r - 1][c]);
                min[r][c] = integer[r][c] + minValue;
            }
        }

        System.out.println(min[row - 1][column - 1]);
    }


    public static int minPathSum(int[][] grid) {
        int row = grid.length;
        int column = grid[0].length;
        int[][] dp = new int[row][column];
        //起点
        dp[0][0] = grid[0][0];
        //第一列
        for(int c = 1; c<column;c++)
            dp[0][c] = dp[0][c-1] + grid[0][c];
        //第一行
        for(int r = 1; r<row;r++)
            dp[r][0] = dp[r-1][0] + grid[r][0];
        for(int i = 1; i<row;i++){
            for(int j = 1;j<column;j++){
                dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }
        return dp[row-1][column-1];
    }


    /**
     * 删除链表的倒数第N个节点
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
         //假如有5个节点 逆向思维法倒数第二 正数第四
        int nodeCount = 1;
        //null的ListNode
        ListNode tempNode = head;
        while (true) {
             if (tempNode.next != null) {
                 tempNode = tempNode.next;
                 nodeCount++;
             } else {
                 break;
             }
        }
        ListNode headPointNode = new ListNode(0, head);
        ListNode node = null;
        for (int i = 0; i < nodeCount - n; i++) {
             headPointNode = headPointNode.next;
        }
        node = headPointNode.next.next;
        if (nodeCount == n) {
            head = node;
        } else {
            headPointNode.next = node;
        }
        return head;
    }

    @Test
    public void testJunit(){
        System.out.println(isValid("[]"));
    }

    /**
     * 有效括号
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        int len = s.length();
        if (len % 2 != 0) return false;
        Map<Character, Character> map = new HashMap<Character, Character>(){{
            put(')', '(');
            put('}', '{');
            put(']', '[');
        }};
        Deque<Character> queue = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            if (map.containsKey(s.charAt(i))){
                if (queue.peek() != map.get(s.charAt(i))) return false;
                queue.pop();
            } else {
                queue.push(s.charAt(i));
            }
        }
        return queue.isEmpty();
    }


    static class ListNode{
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {this.val = val;}
        ListNode(int val, ListNode next) {this.val = val; this.next = next;}
    }

    @Test
    public void testThree() {
//        System.out.println(letterCombinations("23"));
 //       System.out.println(search(new int[]{1}, 0));
        int[][] matrix = {
                {1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}};
        System.out.println(findNumberIn2DArray(matrix, 20));
    }

    /**
     * 返回说有电话数字字母所有的组合  并用字符串数组返回
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        //使用回溯算法
        List<String> combinations = new ArrayList<String>();
        if (digits.length() == 0) return combinations;
        Map<Character, String> phoneMap = new HashMap<Character, String>(){{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        backtrack(combinations, phoneMap, digits, 0, new StringBuffer());
        return combinations;
    }
    public void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            String letters = phoneMap.get(digits.charAt(index));
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }


    /**
     * 
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
         int len = nums.length;
         if (len == 0) return -1;
         if (len == 1) return nums[0] == target ? 0 : -1;
         int l = 0; int r = len - 1;
         while (l <= r) {
             //主要的一步2分
             int mid = (l + r) / 2;
             if (target == nums[mid]) return mid;
             if (nums[0] <= nums[mid]) {
                 //这部分为有序的 否则非有序
                 if (nums[0] <= target && target <= nums[mid]) {
                     r = mid - 1;
                 } else {
                     l = mid + 1;
                 }
             } else {
                 if (nums[mid] <= target && target <= nums[len-1]) {
                     l = mid + 1;
                 } else {
                     r = mid - 1;
                 }
             }
         }
         return -1;
    }

    /**
     * 在排序数组中查找元素的第一个和最后一个位置
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int len = nums.length;
          if (len == 0) return new int[]{-1, -1};
          if (len == 1) return nums[0] == target ? new int[]{0, 0} : new int[]{-1, -1};
          int l = 0, r = len - 1;
          List<Integer> list = new ArrayList<Integer>();
          while (l <= r) {
              int midLeft = (l + r) / 2, midRight = (l + r) / 2;
              if (target == nums[midLeft])
                  list.add(midLeft);
              if (target == nums[midRight])
                  list.add(midRight);
              if (nums[0] <= target && target <= nums[midRight]) {
                  r = midRight - 1;
              }
              if (nums[midLeft] <= target && target <= nums[len-1]) {
                  l = midLeft + 1;
              }
          }
          if (r==0&&l==0) {

          }
          return new int[]{-1, -1};
    }

    /**
     * 数组中重复出现的数字
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        int report = -1;
        Set set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                report = nums[i];
                break;
            }
        }
        return report;
    }

    /**
     * 二维数组中的查找
     * @param matrix
     * @param target
     * @return
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        boolean flag = false;
        int len = matrix.length;
        for (int i = 0; i < len; i++) {
            flag |= binaryQuery(matrix[i], target);
        }
        return flag;
    }

    public boolean binaryQuery(int[] nums, int target) {
        int len = nums.length;
        boolean flag = false;
        if (len == 0 || (len == 1 && nums[0] != target)) {
             return flag;
        }
        int l = 0;
        int r = len -1;
        while (l<=r){
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
               flag = true;
               break;
            }
            if (nums[0] <= target && target <= nums[mid]) {
                r = mid -1;
            } else {
                l = mid + 1;
            }
        }
        return flag;
    }

    /**
     * 替换空格
     * @param s
     * @return
     */
    public String replaceSpace(String s) {
        return s.replaceAll(" ", "%20");
    }

    @Test
    public void test(){
        //System.out.println(replaceSpace("We are happy."));
//        int[] i = reversePrint(new ListNode(1, new ListNode(3, new ListNode(2, null))));
//        for (int j = 0; j < i.length; j++) {
//            System.out.println(i[j]);
//        }

    }

    private Map<Integer, Integer> indexMap;

    public class TreeNode {
       int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }
    }

    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        if (preorder_left > preorder_right) {
            return null;
        }

        // 前序遍历中的第一个节点就是根节点
        int preorder_root = preorder_left;
        // 在中序遍历中定位根节点
        int inorder_root = indexMap.get(preorder[preorder_root]);

        // 先把根节点建立出来
        TreeNode root = new TreeNode(preorder[preorder_root]);
        // 得到左子树中的节点数目
        int size_left_subtree = inorder_root - inorder_left;
        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_root - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
        return root;
    }

    //重建二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }


    @Test
    public void testTwo() {
        ListNode listNode = mergeTwoLists(new ListNode(8, null), new ListNode(5, null));
        //ListNode listNode = mergeTwoLists(new ListNode(1, new ListNode(2, new ListNode(4, null))), new ListNode(1, new ListNode(3, new ListNode(4, null)) ));
        while (listNode != null) {
            System.out.print(listNode.val + " ");
            listNode = listNode.next;
        }
    }

    //合并两个有序链表
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null & list2 == null) return null;
        if ((list1 != null && list2 == null) || (list1 == null && list2 != null)) return list1 == null ? list2 : list1;
        ListNode head1 = null;
        ListNode target = list2;
        ListNode tempNode = new ListNode(0, list2);
        while (list1 != null) {
            head1 = list1.next;
            list1.next = null;
            while (target != null) {
                //list的head引用后移
                if (list1.val >= target.val && ((target.next != null && list1.val <= target.next.val) || target.next == null)) {
                    list1.next = target.next;
                    target = list1;
                    tempNode = tempNode.next;
                    tempNode.next = list1;
                    //target = list1;
                    list1 = head1;
                    break;
                }
                if (list1.val < target.val) {
                    list1.next = target;
                    target = list1;
                    //tempNode = tempNode.next;
                    tempNode.next = list1;
                    //
                    list2 = list1;
                    list1 = head1;
                    break;
                }
                if (list1.val > target.val){
                    tempNode = tempNode.next;
                    target = target.next;
                }
            }
        }
        return list2;
    }



    public int[] reversePrint(ListNode head) {
        Deque<Integer> v = new ArrayDeque<>();
        int count = 0;
        if (head == null ) return new int[]{};
        do{
           v.push(head.val);
           head = head.next;
           count++;
        } while (head != null);
        int[] i = new int[count];
        for (int j = 0; j < count; j++) {
            i[j] = v.pop();
        }
        return i;
    }

}
