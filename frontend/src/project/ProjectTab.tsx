import { octokit } from '@/shared/apis/octokit';
import {
  GITHUB_OWNER,
  GITHUB_REPO,
  GITHUB_DIR_SAMPLE_SHA,
} from '@/shared/constants/github';
import { ReposContent } from '@/shared/interfaces/repos-content';
import { TreeNode } from '@/shared/interfaces/tree';
import { buildTreeFromArray } from '@/shared/utils/tree';
import {
  FileExcelFilled,
  FileFilled,
  FileOutlined,
  FolderFilled,
} from '@ant-design/icons';
import { Button, Layout } from 'antd';
import { Content } from 'antd/es/layout/layout';
import Sider from 'antd/es/layout/Sider';
import { useState, useEffect } from 'react';
import { useQuery } from 'react-query';

export const ProjectTab = () => {
  const [tree, setTree] = useState<TreeNode[]>([]);
  const [content, setContent] = useState<string>('');

  const { isPending, error, data } = useQuery({
    queryKey: 'project',
    queryFn: async () => {
      const response = await octokit.rest.git.getTree({
        owner: GITHUB_OWNER,
        repo: GITHUB_REPO,
        tree_sha: GITHUB_DIR_SAMPLE_SHA,
        recursive: '0',
      });
      return response.data;
    },
  });

  useEffect(() => {
    if (data) {
      setTree(buildTreeFromArray(data.tree));
    }
  }, [data]);

  const renderTree = (nodes: TreeNode[]) => {
    return nodes.map((node) => {
      return (
        <div key={node.path} className="px-2">
          <Button
            icon={node.type === 'tree' ? <FolderFilled /> : <FileOutlined />}
            onClick={async (
              e: React.MouseEvent<HTMLButtonElement, MouseEvent>,
            ) => {
              e.preventDefault();
              if (node.type === 'blob') {
                const data: any = (
                  await octokit.rest.repos.getContent({
                    owner: GITHUB_OWNER,
                    repo: GITHUB_REPO,
                    path: `sample/${node.path}`,
                  })
                ).data;

                setContent(
                  JSON.stringify(JSON.parse(atob(data.content)), null, 4),
                );
              }
            }}
          >
            {node.path.split('/').pop()}
          </Button>
          {node.children.length > 0 && renderTree(node.children)}
        </div>
      );
    });
  };

  return (
    <Layout>
      <Sider
        width={'300px'}
        theme="light"
        style={{ background: 'lightGrey', borderTopLeftRadius: '8px' }}
        className="p-2"
      >
        {renderTree(tree)}
      </Sider>
      <Content
        className="bg-[#f0f0f0] min-h-[600px] p-2"
        style={{ borderTopRightRadius: '8px' }}
      >
        <pre>{content}</pre>
      </Content>
    </Layout>
  );
};
