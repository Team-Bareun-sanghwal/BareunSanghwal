import { Head } from "@/components/main/Head/Head";
  
export default async function MainLayout({
    children,
}: {
    children: React.ReactNode;
  }){
    return(
    <div>
        <Head/>
        {children}
    </div>
    )
}
