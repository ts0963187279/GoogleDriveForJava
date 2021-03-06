/*
 * Copyright (C) 2017 RS Wong <ts0963187279@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.walton.java.GoogleDriveForJava.processor;

import com.walton.java.GoogleDriveForJava.model.SearchFileInfo;
import poisondog.core.Mission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetDirectory implements Mission<SearchFileInfo>{
    private Map<String,SearchFileInfo> stringFileInfoMap;
    private String path = "GoogleDrive/";
    public GetDirectory(Map<String,SearchFileInfo> stringFileInfoMap){
        this.stringFileInfoMap = stringFileInfoMap;
    }
    public String execute(SearchFileInfo searchFileInfo){
        List<String> directoryTmp = new ArrayList<String>();
        try{
            SearchFileInfo parentInfo = stringFileInfoMap.get(searchFileInfo.getParentId());
            int i;
            for(i=0;!parentInfo.isParentIsRoot();i++) {
                directoryTmp.add(parentInfo.getTitle());
                parentInfo = stringFileInfoMap.get(parentInfo.getParentId());
            }
            directoryTmp.add(parentInfo.getTitle());
            parentInfo = stringFileInfoMap.get(parentInfo.getParentId());
            directoryTmp.add(parentInfo.getTitle());
            for(int j=i+1;j>=0;j--)
                path += directoryTmp.get(j)+"/";
        }catch (NullPointerException e){
            try {
                path += stringFileInfoMap.get(searchFileInfo.getParentId()).getTitle() + "/";
            }catch (NullPointerException e1){
                return path;
            }
        }
        return path;
    }
}
