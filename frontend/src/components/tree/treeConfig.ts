interface ITreeConfig {
    source: string;
    scaleValue: number;
    position: number[];
}

const configLevel1 = {
    source :'./assets/littleTree.glb',
    scaleValue: 1,
    position : [0.3,0.2, 1.2],
}
const configLevel2 = {
    source :'./assets/littleTree.glb',
    scaleValue: 1.4,
    position : [0.3,0.2, 1.2],
}
const configLevel3 = {
    source :'./assets/orgTree.glb',
    scaleValue: 0.6,
    position : [0.1,-0.0, 1.1],
}
const configLevel4 = {
    source :'./assets/orgTree.glb',
    scaleValue: 0.8,
    position : [0.3,-0.2, 1.2],
}
const configLevel5 = {
    source :'./assets/BigTree.glb',
    scaleValue: 0.3,
    position : [0.3,-0.2, 1.2],
}
export const treeConfig:ITreeConfig[] = [
    configLevel1,
    configLevel2,
    configLevel3,
    configLevel4,
    configLevel5,
]