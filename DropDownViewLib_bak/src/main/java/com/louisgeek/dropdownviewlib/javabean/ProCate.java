package com.louisgeek.dropdownviewlib.javabean;

import java.util.List;

public class ProCate {

    /**
     * cateid : 0_1
     * catename : 瓜菜类
     * children : [{"cateid":"1_2","catename":"甜瓜"},{"cateid":"1_3","catename":"黄瓜"}]
     */

    private List<CatesBean> cates;

    public List<CatesBean> getCates() {
        return cates;
    }

    public void setCates(List<CatesBean> cates) {
        this.cates = cates;
    }

    public static class CatesBean {
        private String cateid;
        private String catename;
        /**
         * cateid : 1_2
         * catename : 甜瓜
         */

        private List<ChildrenBean> children;

        public String getCateid() {
            return cateid;
        }

        public void setCateid(String cateid) {
            this.cateid = cateid;
        }

        public String getCatename() {
            return catename;
        }

        public void setCatename(String catename) {
            this.catename = catename;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            private String cateid;
            private String catename;

            public String getCateid() {
                return cateid;
            }

            public void setCateid(String cateid) {
                this.cateid = cateid;
            }

            public String getCatename() {
                return catename;
            }

            public void setCatename(String catename) {
                this.catename = catename;
            }
        }
    }
}
