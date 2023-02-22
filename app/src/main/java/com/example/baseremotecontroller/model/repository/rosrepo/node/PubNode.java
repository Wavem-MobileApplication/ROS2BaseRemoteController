package com.example.baseremotecontroller.model.repository.rosrepo.node;

import org.ros2.rcljava.node.BaseComposableNode;

public class PubNode extends BaseComposableNode {
    private static PubNode instance;

    private PubNode() {
        super("BaseRemoteController_pubnode");
    }

    public static PubNode getInstance() {
        if (instance == null) {
            instance = new PubNode();
        }
        return instance;
    }
}
