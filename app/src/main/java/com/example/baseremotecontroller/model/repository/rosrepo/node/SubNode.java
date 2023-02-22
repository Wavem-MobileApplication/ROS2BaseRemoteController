package com.example.baseremotecontroller.model.repository.rosrepo.node;

import org.ros2.rcljava.node.BaseComposableNode;

public class SubNode extends BaseComposableNode {
    private static SubNode instance;

    private SubNode() {
        super("BaseRemoteController_subnode");
    }

    public static SubNode getInstance() {
        if (instance == null) {
            instance = new SubNode();
        }
        return instance;
    }
}
