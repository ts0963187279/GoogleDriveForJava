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

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.walton.java.GoogleDriveForJava.model.SearchFileInfo;
import poisondog.core.Mission;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDriveFilesMap implements Mission<SearchFileInfo>{
    private Drive drive;
    public GetDriveFilesMap(Drive drive){
        this.drive = drive;
    }
    public Map<String,SearchFileInfo> execute(SearchFileInfo folder){
        Map<String,SearchFileInfo> fileInfoMap = new HashMap<String,SearchFileInfo>();
        PackageFileToSearchFileInfo packageFileToSearchFileInfo = new PackageFileToSearchFileInfo(drive);
        fileInfoMap.put(folder.getId(),folder);
        ChildList childList;
        try {
            childList = drive.children().list(folder.getId()).execute();
            for(ChildReference childReference : childList.getItems()){
                String childId = childReference.getId();
                File file = drive.files().get(childId).execute();
                SearchFileInfo searchFileInfo = packageFileToSearchFileInfo.execute(file);
                fileInfoMap.put(searchFileInfo.getId(), searchFileInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInfoMap;
    }
}
