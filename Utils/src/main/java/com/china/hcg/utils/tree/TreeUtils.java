package com.china.hcg.utils.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @autor hecaigui
 * @date 2023/10/17
 * @description
 */
public class TreeUtils {
//demo
//    List<Tree> listTree = new ArrayList<>();
//        for (SysRes sysRes : sysResList) {
//        Tree trees = new Tree(id);
//                    .id(sysRes.getSysResId())
//                .parentId(sysRes.getParentId())
//                .name(sysRes.getName())
//                .orderValue(sysRes.getOrderValue())
//                .children(new ArrayList<>())
//                .build();
//        listTree.add(trees);
//    }

    private List<Tree> getTrees(List<Tree> listTree) {
        List<Tree> trees = buildTree(listTree, "-1");
//排序1
//        for (Tree trees : listTree) {
//            List<Tree> subList = trees.getChildren();
//            if (subList.size() > 0) {
//                Collections.sort(subList, comparator);
//            }
//        }
//        Collections.sort(treess, comparator);
//排序2
        //sortSysResTree(treess);

        return trees;
    }
    public static List<Tree> buildTree(List<Tree> nodes, String idParam) {

        if (nodes == null) {
            return null;
        }
        List<Tree> topNodes = new ArrayList<>();
        for (Tree children : nodes) {
            String pid = children.getParentId();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);
                continue;
            }
            for (Tree parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setChildren(true);
                    continue;
                }
            }
        }
        return topNodes;
    }
//
//    private void sortSysResTree(List<Tree> treess) {
//
//        Collections.sort(treess, comparator);
//        for (Tree trees : treess) {
//            List<Tree> subSyslist = trees.getChildren();
//            if (subSyslist.size() > 0) {
//                Collections.sort(subSyslist, comparator);
//                sortSysResTree(subSyslist);
//            }
//        }
//    }
//
//    Comparator comparator = new Comparator<Tree>() {
//        @Override
//        public int compare(Tree o1, Tree o2) {
//
//            int f1 = o1.getOrderValue();
//            int f2 = o2.getOrderValue();
//
//            if (f1 < f2) {
//                return 1;
//            }
//            if (f1 > f2) {
//                return -1;
//            }
//            return 0;
//        }
//    };
}
