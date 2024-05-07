export interface TreeNode {
  path: string;
  mode: string;
  type: string;
  sha: string;
  url: string;
  children: TreeNode[];
}

export interface Tree {
  sha: string;
  url: string;
  tree: TreeNode[];
  truncated: boolean;
}
