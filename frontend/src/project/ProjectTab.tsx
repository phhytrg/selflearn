import { gitExchangeApi } from "@/shared/apis";
import { buildTreeFromArray } from "@/shared/utils/tree";
import { FolderFilled, FileOutlined } from "@ant-design/icons";
import { Button, Layout, Skeleton } from "antd";
import { Content } from "antd/es/layout/layout";
import Sider from "antd/es/layout/Sider";
import { useState, useEffect } from "react";
import { TreeNode, NodeType, FileContent } from "./interfaces";
import { useGetTree } from "./queries";


export const ProjectTab = () => {
  const [tree, setTree] = useState<TreeNode[]>([]);
  const [content, setContent] = useState<string | null>('');

  const { data } = useGetTree();

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
              if (node.type === NodeType.Blob) {
                setContent(null);
                const data: FileContent = (
                  await gitExchangeApi.getContent(`sample/${node.path}`)
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
        <pre>{content ?? <Skeleton />}</pre>
      </Content>
    </Layout>
  );
};
