public class ClassA {
    public void method(ClassC c) {
        System.out.println("调用ClassA.method()");
        System.out.println(c);
        //打印的是ClassB和ClassC的加载器 segmentfault.Question1010000017720845.MyClassLoader_2@23fc625e
        System.out.println(c.getClass().getClassLoader());
        //只有在ClassA中显式的加载ClassC才会报 classC not found
        try {
            Class classC = this.getClass().getClassLoader().loadClass("segmentfault.Question1010000017720845.ClassC");
        } catch (ClassNotFoundException e) {
            System.out.println("classC not found");
        }
    }
}

public class ClassB {
    public void method(){
        System.out.println("调用ClassB...");
        ClassA a = new ClassA();
        //ClassC not found 也有可能在这里就报了，比如说ClassC确实没找到
        ClassC c = new ClassC();
        //这里调用了ClassA的方法，必然需要先加载ClassA，
        //说明ClassA的加载器是ClassB加载器的父加载器
        a.method(c);
    }
}

public class ClassC {
    public void method(){
        System.out.println("调用ClassC.method()");
    }
}
