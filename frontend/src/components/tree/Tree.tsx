'use client';
import { CameraControls } from './CameraControls';
import { Canvas, useThree } from '@react-three/fiber';
import { Suspense, useState, useEffect, useRef } from 'react';
import { OrbitControls, Loader, useGLTF } from '@react-three/drei';
import { MeshStandardMaterial } from 'three';
import * as THREE from 'three';

import { useLoader } from '@react-three/fiber';
import { TextureLoader, BackSide } from 'three';

function SkyDome() {
  const texture = useLoader(TextureLoader, '/assets/sky.jpg');
  return (
    <mesh>
      <sphereGeometry args={[500, 60, 40]} />
      <meshBasicMaterial map={texture} side={BackSide} />
    </mesh>
  );
}
function Model({ color }: { color: string }) {
  const { scene } = useGLTF('/assets/orgTree.glb');
  scene.position.set(-4.2, 0.2, -3);
  scene.scale.set(1.6, 1.6, 1.6);
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
  return <primitive object={scene} />;
}
function OutDoor() {
  const { scene } = useGLTF('/assets/outdoor.glb');
  return <primitive object={scene} />;
}

function GiftBox() {
  const { scene } = useGLTF('/assets/giftbox.glb');
  scene.position.set(-1.2, 1, 4.8);
  scene.scale.set(0.8, 0.8, 0.8);
  return <primitive object={scene} />;
}
export default function Tree({ color }: { color: string }) {
  const [position, setPosition] = useState({ x: 20, y: 16, z: 24 });
  const [target, setTarget] = useState({ x: 0, y: 0, z: 0 });

  const handleOnclick1 = () => {
    setPosition({ x: 5, y: 2, z: 0 });
    setTarget({ x: 10, y: 2, z: 0 });
  };

  const handleOnclick2 = () => {
    setPosition({ x: 3, y: 2, z: 0 });
    setTarget({ x: 5, y: 2, z: 0 });
  };
  return (
    <>
      <div
        style={{
          width: '100%',
          height: '100vh',
          overflow: 'hidden',
          position: 'relative',
        }}
      >
        <Canvas camera={{ position: [10, 1, 14], fov: 95, near: 1, far: 1000 }}>
          <ambientLight intensity={0.6} />
          <spotLight position={[10, 10, 10]} angle={0.8} penumbra={1} />
          <directionalLight position={[-10, 10, 10]} intensity={2} />
          <pointLight position={[-10, -10, -10]} />
          <Suspense fallback={null}>
            <Model color={color} />
            <GiftBox />
            <OutDoor />
            <SkyDome />
          </Suspense>
          <CameraControls position={position} target={target} />
        </Canvas>
        <Loader />
      </div>
    </>
  );
}
