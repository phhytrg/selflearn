package com.selflearn.backend.gitExchange.daos;

import com.selflearn.backend.gitExchange.dtos.ContentResponse;
import com.selflearn.backend.gitExchange.dtos.CreateBlobResponse;
import com.selflearn.backend.gitExchange.dtos.CreateCommitResponse;
import com.selflearn.backend.gitExchange.dtos.CreateTreeRequest;
import com.selflearn.backend.gitExchange.dtos.CreateTreeResponse;
import com.selflearn.backend.gitExchange.dtos.GitRepoTrees;

import java.util.List;

public interface GitExchangeDao {
    GitRepoTrees getRepoTrees(String sha);
    String getLatestSha();
    String getLatestSampleDirSha();
    CreateBlobResponse createBlob(String content);
    CreateTreeResponse createTree(String baseTreeSha, List<CreateTreeRequest.TreeNodeRequest> tree);
    CreateCommitResponse createCommit(String treeSha, String parentCommitSha, String commitMessage);
    CreateCommitResponse updateReference(CreateCommitResponse createCommitResponse);
    ContentResponse getContentFromPath(String path);
    ContentResponse getContentFromUrl(String url);
    ContentResponse[] getContentsFromPath(String path);
}
