export interface TreeNode {
  path: string;
  mode: string;
  type: string;
  sha: string;
  url: string;
  children: TreeNode[];
}
