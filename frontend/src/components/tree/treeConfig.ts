interface ITreeConfig {
  source: string;
  scaleValue: number;
  name: string;
  position: number[];
}
const configLevel1 = {
  source: './assets/littleTree.glb',
  scaleValue: 1,
  name: '아기나무',
  position: [0.3, 0.7, 1.2],
};
const configLevel2 = {
  source: './assets/littleTree.glb',
  scaleValue: 1.4,
  name: '아이나무',
  position: [0.3, 0.7, 1.2],
};
const configLevel3 = {
  source: './assets/orgTree.glb',
  scaleValue: 0.6,
  name: '소년나무',
  position: [0.1, 0.5, 1.1],
};
const configLevel4 = {
  source: './assets/orgTree.glb',
  scaleValue: 0.8,
  name: '청년나무',
  position: [0.3, 0.3, 1.2],
};
const configLevel5 = {
  source: './assets/orgTree.glb',
  scaleValue: 1.1,
  name: '어른나무',
  position: [0.3, 0.3, 1.2],
};
export const treeConfig: ITreeConfig[] = [
  configLevel1,
  configLevel2,
  configLevel3,
  configLevel4,
  configLevel5,
];
