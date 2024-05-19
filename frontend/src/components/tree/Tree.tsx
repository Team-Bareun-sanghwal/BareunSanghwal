'use client';
import Item from '@/components/point/Item/Item';
import { CameraControls } from './CameraControls';
import { Canvas, useThree } from '@react-three/fiber';
import { Suspense, useState, useEffect, useRef } from 'react';
import { OrbitControls, Loader, useGLTF } from '@react-three/drei';
import { MeshStandardMaterial } from 'three';
import * as THREE from 'three';
import { treeConfig } from './treeConfig';
import { useLoader } from '@react-three/fiber';
import { TextureLoader, BackSide } from 'three';
import {useFrame} from '@react-three/fiber';

interface IItem {
  key: 'gotcha_streak' | 'gotcha_tree' | 'recovery' | 'none';
  name: string;
  introduction: string;
  description: string;
  price: number;
}
function SkyDome({time}: {time : 'morning' | 'lunch' | 'dinner' | 'night' | 'midnight'}) {
  const texture = useLoader(TextureLoader, `/assets/background/${time}.png`);
  return (
    <mesh>
      <sphereGeometry args={[100, 60, 40]} />
      <meshBasicMaterial map={texture} side={BackSide} />
    </mesh>
  );
}

function Island() {
  const { scene } = useGLTF('/assets/island.glb');
  const rate = 0.5
  scene.scale.set(rate, rate, rate)
  scene.position.set(0, 0.5, 0);
  return <primitive object={scene} />;
}
function MyTree( {color, level}: {color: string, level: number}) {
  const {source, scaleValue, position} = treeConfig[level-1];
  const { scene } = useGLTF(source);
  scene.scale.set(scaleValue, scaleValue, scaleValue);
  scene.position.set(position[0], position[1], position[2]);

  scene.traverse((child: THREE.Object3D) => {
    if ((child as THREE.Mesh).isMesh) {
      const name = child.name;
      switch (name) {
        case 'Trunk':
          (child as THREE.Mesh).material = new MeshStandardMaterial({
            color: new THREE.Color(0xdeb887),
          });
          break;
        case 'Leaves':
          (child as THREE.Mesh).material = new MeshStandardMaterial({
            color: new THREE.Color(color),
            metalness: 0,
            roughness: 0,
          });
          break;
        case 'Rock':
          (child as THREE.Mesh).material = new MeshStandardMaterial({
            color: 'gray',
          });
          break;
        case 'Grass':
          (child as THREE.Mesh).material = new MeshStandardMaterial({
            color: new THREE.Color(0x8a9a5b),
          });
          break;
        default:
          (child as THREE.Mesh).material = new MeshStandardMaterial({
            color: 'red',
          });
          break;
      }
    }
  });
  return <primitive object={scene}/>;
}

function Group({color, level}: {color : string, level : number}){
  const groupRef = useRef<THREE.Group>(null);
  useFrame(() => {
    if (groupRef.current) {
      groupRef.current.rotation.y += 0.001;
    }
  });
  return (
    <group ref={groupRef}>
      <MyTree color={color} level={level} />
      <Island />
      <GiftBox/>
    </group>
  )
}

function GiftBox() {
  const { scene } = useGLTF('/assets/giftbox.glb');
  scene.position.set(-0.8, 0.82, 0.2);
  scene.scale.set(0.4, 0.4, 0.4);
  return <primitive object={scene} />;
}


export default function Tree({ color,level, time, ItemList}: {color : string, level : number, time : 'morning' | 'lunch' | 'dinner' | 'night' | 'midnight', ItemList : IItem[]}) {
  const [position, setPosition] = useState({ x: 10, y: 2, z: 12 });
  const [target, setTarget] = useState({ x: 0, y: 0, z: 0 });
  const [selectedItem , setSelectedItem] = useState<'gotcha_streak' | 'gotcha_tree' | 'recovery' | 'none'>('none');
  return (
    <>
       <Canvas camera={{ position: [15, 0, 20], fov: 95, near: 1, far: 1000 }}>
          <ambientLight intensity={0.6} />
          <spotLight position={[10, 10, 10]} angle={0.8} penumbra={1} />
          <directionalLight position={[-10, 10, 10]} intensity={time==='night'? 0.6 : 2} />
          <pointLight position={[-10, -10, -10]} />
          <Suspense fallback={null}>
            <Group color={color} level={level} />
            <SkyDome time ={time}/>
          </Suspense>
          <CameraControls position={position} target={target} />
        </Canvas>
        <Loader />
        <div className="absolute bottom-0 w-full gap-3 p-3 ">
          <div className="flex flex-col justify-center gap-4">
            {ItemList?.map((item: IItem) => (
              <Item
                key={item.key}
                keyname={item.key}
                name={item.name}
                introduction={item.introduction}
                description={item.description}
                price={item.price}
                selectedItem={selectedItem}
                setSelectedItem={setSelectedItem}
              />
            ))}
        </div>
    </div>
    </>
  );
}
