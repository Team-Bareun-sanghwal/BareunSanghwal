import { OrbitControls } from '@react-three/drei';
import { useThree } from '@react-three/fiber';
import { useEffect, useRef } from 'react';
import * as THREE from 'three'; // Import THREE for THREE.Vector3
import gsap from 'gsap';

interface CameraControlsProps {
  position: { x: number; y: number; z: number };
  target: { x: number; y: number; z: number };
}

const CameraControls = ({ position, target }: CameraControlsProps) => {
  const { camera, gl } = useThree();
  const orbitControlsRef = useRef<any>(null);

  useEffect(() => {
    if (orbitControlsRef.current) {
      // Set the target of the underlying OrbitControls instance
      const controls = orbitControlsRef.current;
      controls.target.set(target.x, target.y, target.z);
      controls.update(); // Call update to ensure the new target is used

      // Animate camera position
      gsap.to(camera.position, {
        duration: 2,
        x: position.x,
        y: position.y,
        z: position.z,
        ease: 'power3.inOut',
        onComplete: () => controls.update(), // Update controls after animation
      });
    }
  }, [position, target]);

  return (
    <OrbitControls ref={orbitControlsRef} args={[camera, gl.domElement]} />
  );
};

export { CameraControls };
