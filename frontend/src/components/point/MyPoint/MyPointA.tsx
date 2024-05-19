'use client'
import { motion, AnimatePresence } from 'framer-motion';
export const MyPointA = ({point} : {point : number}) => {
    return (
        <div className="absolute z-20 top-4 right-4 flex p-2 w-auto h-12 min-w-24 text-2xl items-center bg-white rounded-full">
            <div className="bg-custom-matcha w-8 h-8 rounded-full text-white text-center content-center mr-2">
            P
          </div>
          {point}
        </div>
    )
}