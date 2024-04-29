'use client';
import { Canvas } from '@react-three/fiber';
import { Suspense, useState, useEffect, useRef } from 'react';
import { OrbitControls, Loader, useGLTF } from '@react-three/drei';
import { MeshStandardMaterial } from 'three';
import * as THREE from 'three';

function Model({ color, rotation }: { color: string; rotation: number }) {
  const { scene } = useGLTF('/assets/orgTree.glb');
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

  return <primitive object={scene} rotation={[0, rotation, 0]} />;
}

export default function Tree({ color }: { color: string }) {
  const [rotation, setRotation] = useState(0);
  const rotateRef = useRef({ rotate: false, direction: 0 });

  useEffect(() => {
    const animate = () => {
      if (rotateRef.current.rotate) {
        console.log('animate  ');
        setRotation(
          (rotation) => rotation + 0.01 * rotateRef.current.direction,
        );
        requestAnimationFrame(animate);
      }
    };

    requestAnimationFrame(animate);
    return () => {
      rotateRef.current.rotate = false;
    };
  }, []);

  const startRotation = (direction: number) => {
    console.log('startRotation');
    rotateRef.current = { rotate: true, direction };
    requestAnimationFrame(() => {});
  };

  const stopRotation = () => {
    console.log('stopRotation');
    rotateRef.current.rotate = false;
  };

  return (
    <div style={{ height: '500px', width: '100%' }}>
      <button
        onMouseDown={() => startRotation(-1)}
        onMouseUp={stopRotation}
        className="text-3xl border-2 border-black w-10 h-10 rounded-full text-center content-center top-10 ml-8"
      >
        A
      </button>
      <button
        onMouseDown={() => startRotation(1)}
        onMouseUp={stopRotation}
        className="text-3xl border-2 border-black w-10 h-10 rounded-full text-center content-center top-10 ml-28"
      >
        B
      </button>
      <Canvas camera={{ position: [4, 2, 14], fov: 75, near: 1, far: 1000 }}>
        <ambientLight intensity={0.3} />
        <spotLight position={[10, 10, 10]} angle={0.4} penumbra={1} />
        <directionalLight position={[-10, 10, 10]} intensity={2} />
        <pointLight position={[-10, -10, -10]} />
        <Suspense fallback={null}>
          <Model color={color} rotation={rotation} />
        </Suspense>
        <OrbitControls />
      </Canvas>
      <Loader />
    </div>
  );
}
