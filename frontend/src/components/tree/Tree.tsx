'use client';

import { Canvas } from '@react-three/fiber';
import { Suspense, useMemo } from 'react';
import { OrbitControls, Loader, useGLTF } from '@react-three/drei';
import { TextureLoader, MeshStandardMaterial } from 'three';
import * as THREE from 'three';

function Model(color: { color: string }) {
  const newColor = { color: new THREE.Color(color.color) };
  const { scene } = useGLTF('/assets/orgTree.glb');
  scene.traverse((child: THREE.Object3D) => {
    if ((child as THREE.Mesh).isMesh) {
      const Name = child.name;
      switch (Name) {
        case 'Trunk':
          (child as THREE.Mesh).material = new MeshStandardMaterial({
            color: new THREE.Color(0xdeb887),
          });
          break;
        case 'Leaves':
          (child as THREE.Mesh).material = new MeshStandardMaterial({
            color: color.color,
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

export default function Tree(color: string) {
  return (
    <div style={{ height: '500px', width: '100%' }}>
      <Canvas camera={{ position: [4, 2, 14], fov: 75, near: 1, far: 1000 }}>
        <ambientLight intensity={0.3} />
        <spotLight position={[10, 10, 10]} angle={0.4} penumbra={1} />
        <directionalLight position={[-10, 10, 10]} intensity={2} />
        <pointLight position={[-10, -10, -10]} />
        <Suspense fallback={null}>
          <Model color={color} />
        </Suspense>
        <OrbitControls />
      </Canvas>
      <Loader />
    </div>
  );
}
