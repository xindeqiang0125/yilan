package com.xindq.yilan.activity.list;

import com.xindq.yilan.domain.FileDetail;

import java.util.List;

interface OnReceiveDataLisenter {
    void receiveData(List<FileDetail> list);
}
