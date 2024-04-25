'use client';

import { Canvas } from '@react-three/fiber';
import { Suspense, useMemo } from 'react';
import { OrbitControls, Loader, useGLTF } from '@react-three/drei';
import { TextureLoader, MeshStandardMaterial } from 'three';
import * as THREE from 'three';

function Model(color) {
  const { scene } = useGLTF('/assets/orgTree.glb');
  scene.traverse((child) => {
    if (child.isMesh) {
      const Name = child.name;
      switch (Name) {
        case 'Trunk':
          child.material = new MeshStandardMaterial({
            color: 'brown',
          });
          break;
        case 'Leaves':
          child.material = new MeshStandardMaterial({
            color: color.color,
            metalness: 2,
            roughness: 0,
          });
          break;
        case 'Rock':
          child.material = new MeshStandardMaterial({
            color: 'gray',
          });
          break;
        case 'Grass':
          child.material = new MeshStandardMaterial({
            color: 'lightgreen',
          });
          break;
        default:
          child.material = new MeshStandardMaterial({
            color: 'red',
          });
          break;
      }
    }
  });

  return <primitive object={scene} />;
}

export default function Tree({ color }) {
  return (
    <div style={{ height: '500px', width: '100%' }}>
      <Canvas camera={{ position: [4, 2, 14], fov: 75, near: 1, far: 1000 }}>
        <ambientLight intensity={0.3} />
        <spotLight position={[10, 10, 10]} angle={0.4} penumbra={1} />
        <directionalLight position={[-5, 5, 5]} intensity={1.5} />
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
