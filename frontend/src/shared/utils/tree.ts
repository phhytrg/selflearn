import { TreeNode } from '../interfaces/tree';

export const buildTreeFromArray = (nodes?: any[]): TreeNode[] => {
  const tree: TreeNode[] = [];
  const map: { [key: string]: TreeNode } = {};

  if(!nodes) return tree;

  nodes.forEach((nodeData) => {
    map[nodeData.path] = {
      path: nodeData.path,
      mode: nodeData.mode,
      type: nodeData.type,
      sha: nodeData.sha,
      url: nodeData.url,
      children: [],
    };
  });

  nodes.forEach((nodeData) => {
    const node = map[nodeData.path];
    const parentPath = nodeData.path.split('/').slice(0, -1).join('/');
    if (map[parentPath]) {
      map[parentPath].children.push(node);
    } else {
      tree.push(node);
    }
  });

  return tree;
};
