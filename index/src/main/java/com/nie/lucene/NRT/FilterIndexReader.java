//package com.nie.lucene.NRT;
//
//import org.apache.lucene.index.IndexReader;
//
///**
// * @author zhaochengye
// * @date 2019-05-10 15:10
// */
//public class FilterIndexReader FilterIndexReader {
//
//
//        OpenBitSet dels;
//
//        public MyFilterIndexReader(IndexReader in) {
//
//            super(in);
//
//            dels = new OpenBitSet(in.maxDoc());
//
//        }
//
//        public MyFilterIndexReader(IndexReader in, List<String> idToDelete) throws IOException {
//
//            super(in);
//
//            dels = new OpenBitSet(in.maxDoc());
//
//            for(String id : idToDelete){
//
//                TermDocs td = in.termDocs(new Term("id", id)); //如果能在内存中Cache从Lucene的ID到应用的ID的映射，Reader的生成将快得多。
//
//                if(td.next()){
//
//                    dels.set(td.doc());
//
//                }
//
//            }
//
//        }
//
//        @Override
//
//        public int numDocs() {
//
//            return in.numDocs() - (int) dels.cardinality();
//
//        }
//
//        @Override
//
//        public TermDocs termDocs(Term term) throws IOException {
//
//            return new FilterTermDocs(in.termDocs(term)) {
//
//                @Override
//
//                public boolean next() throws IOException {
//
//                    boolean res;
//
//                    while ((res = super.next())) {
//
//                        if (!dels.get(doc())) {
//
//                            break;
//
//                        }
//
//                    }
//
//                    return res;
//
//                }
//
//            };
//
//        }
//
//        @Override
//
//        public TermDocs termDocs() throws IOException {
//
//            return new FilterTermDocs(in.termDocs()) {
//
//                @Override
//
//                public boolean next() throws IOException {
//
//                    boolean res;
//
//                    while ((res = super.next())) {
//
//                        if (!dels.get(doc())) {
//
//                            break;
//
//                        }
//
//                    }
//
//                    return res;
//
//                }
//
//            };
//
//        }
//
//}
