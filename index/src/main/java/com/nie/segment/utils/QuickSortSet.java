package com.nie.segment.utils;

import com.nie.segment.model.Lexeme;

/**
 *
 * IK分词器专用的Lexem快速排序集合
 * @author zhaochengye
 * @date 2019-05-05 16:58
 */
public class QuickSortSet {
    // 链表头
    private Cell head;
    // 链表尾
    private Cell tail;
    // 链表的实际大小
    private int size;

    public QuickSortSet() {
        this.size = 0;
    }

    /**
     * 向链表集合添加词元
     * @param lexeme
     */
    public boolean addLexeme(Lexeme lexeme) {
        Cell newCell = new Cell(lexeme);
        if (this.size == 0) {
            this.head = newCell;
            this.tail = newCell;
            this.size++;
            return true;

        } else {
            if (this.tail.compareTo(newCell) == 0) {// 词元与尾部词元相同，不放入集合
                //  	System.out.println("1词元与尾部词元相同，不放入集合"+newCell.getLexeme().getBeginPosition()+"-"+newCell.getLexeme().getEndPosition());
                if(newCell.lexeme.getLexemeType() < this.tail.lexeme.getLexemeType()){
                    this.tail.lexeme = newCell.lexeme;
                }
                return false;
            } else if (this.tail.compareTo(newCell) < 0) {// 词元接入链表尾部
                //  System.out.println("2");
                //  	System.out.println("2词元接入链表尾部"+newCell.getLexeme().getBeginPosition()+"-"+newCell.getLexeme().getEndPosition());

                this.tail.next = newCell;
                newCell.prev = this.tail;
                this.tail = newCell;
                this.size++;
                return true;

            } else if (this.head.compareTo(newCell) > 0) {// 词元接入链表头部
                //   	System.out.println("3词元接入链表头部"+newCell.getLexeme().getBeginPosition()+"-"+newCell.getLexeme().getEndPosition());
                //	System.out.println("3词元接入链表头部this.head:"+this.head.getLexeme().getBeginPosition()+"-"+this.head.getLexeme().getEndPosition());

                this.head.prev = newCell;
                newCell.next = this.head;
                this.head = newCell;
                this.size++;
                return true;

            } else {
                // 从尾部上逆
                Cell index = this.tail;
                while (index != null && index.compareTo(newCell) >0) {
                    //  	System.out.println("上逆"+newCell.getLexeme().getBeginPosition()+"-"+newCell.getLexeme().getEndPosition());
                    //  	System.out.println("上逆 prev"+index.prev.getLexeme().getBeginPosition()+"-"+index.prev.getLexeme().getEndPosition());

                    index = index.prev;
                }
                if (index.compareTo(newCell) == 0) {// 词元与集合中的词元重复，通过lexemeType,type越小表示词对应的权重越大,就替换原来的
                    if(newCell.lexeme.getLexemeType() <index.lexeme.getLexemeType()){
                        //  System.out.println("重服了 替换");
                        //&&!(index.lexeme.istargetType(Lexeme.TYPE_MODEL)&&newCell.lexeme.isEnglish() System.out.println("newCell: "+newCell.lexeme);
                        index.lexeme = newCell.lexeme;
                    }
                    return false;

                } else if (index.compareTo(newCell) < 0) {// 词元插入链表中的某个位置
                    // System.out.println("词元插入链表中的某个位置");
                    newCell.prev = index;
                    newCell.next = index.next;
                    index.next.prev = newCell;
                    index.next = newCell;
                    this.size++;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 返回链表头部元素
     * @return
     */
    public Lexeme peekFirst() {
        if (this.head != null) {
            return this.head.lexeme;
        }
        return null;
    }

    /**
     * 取出链表集合的第一个元素
     * @return Lexeme
     */
    public Lexeme pollFirst() {
        if (this.size == 1) {
            Lexeme first = this.head.lexeme;
            this.head = null;
            this.tail = null;
            this.size--;
            return first;
        } else if (this.size > 1) {
            Lexeme first = this.head.lexeme;
            this.head = this.head.next;
            this.size--;
            return first;
        } else {
            return null;
        }
    }

    /**
     * 返回链表尾部元素
     * @return
     */
    public Lexeme peekLast() {
        if (this.tail != null) {
            return this.tail.lexeme;
        }
        return null;
    }

    /**
     * 取出链表集合的最后一个元素
     * @return Lexeme
     */
    public Lexeme pollLast() {
        if (this.size == 1) {
            Lexeme last = this.head.lexeme;
            this.head = null;
            this.tail = null;
            this.size--;
            return last;

        } else if (this.size > 1) {
            Lexeme last = this.tail.lexeme;
            this.tail = this.tail.prev;
            this.size--;
            return last;

        } else {
            return null;
        }
    }

    /**
     * 返回集合大小
     * @return
     */
    public int size() {
        return this.size;
    }

    /**
     * 判断集合是否为空
     * @return
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 返回lexeme链的头部
     * @return
     */
    public Cell getHead() {
        return this.head;
    }

    /**
     * QuickSortSet集合单元
     *
     */
    public static class Cell implements Comparable<Cell> {
        private Cell prev;
        private Cell next;
        private Lexeme lexeme;

        Cell(Lexeme lexeme) {
            if (lexeme == null) {
                throw new IllegalArgumentException("lexeme must not be null");
            }
            this.lexeme = lexeme;
        }

        @Override
        public int compareTo(Cell o) {
            return this.lexeme.compareTo(o.lexeme);
        }

        public Cell getPrev() {
            return this.prev;
        }

        public Cell getNext() {
            return this.next;
        }

        public Lexeme getLexeme() {
            return this.lexeme;
        }
    }
}
