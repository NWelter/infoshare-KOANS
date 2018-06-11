package beginner;

import com.sandwich.koan.Koan;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.sandwich.koan.constant.KoanConstants.__;
import static com.sandwich.util.Assert.assertEquals;

public class AboutObjects {

    @Koan
    public void newObjectInstancesCanBeCreatedDirectly() {
        assertEquals(new Object() instanceof Object, true);
    }

    @Koan
    public void allClassesInheritFromObject() {
        class Foo {
        }

        Class<?>[] ancestors = getAncestors(new Foo());

        //first object inherit from Foo (clazz.getSuperclass)
        //second object inherit from Object (one level up from Foo)

        assertEquals(ancestors[0], Foo.class);
        assertEquals(ancestors[1], Object.class);
    }

    @Koan
    public void objectToString() {
        Object object = new Object();
        // TODO: Why is it best practice to ALWAYS override toString?
        String expectedToString = MessageFormat.format("{0}@{1}", Object.class.getName(), Integer.toHexString(object.hashCode()));

        /*
        QUESTION: Why object.toString printout this message: 
        "java.lang.Object@15db9742" but "java.lang.Object@1b90825c" was expected -> how explains difference? 

        checking code:
        
        Object object = new Object();
        String name = Object.class.getName();
        int hash = object.hashCode();
        String hex2 = Integer.toHexString(hash);
        String hex = Integer.toHexString(object.hashCode());
        String objectString = object.toString();
        System.out.println(name);
        System.out.println(hex);
        System.out.println(hash);
        System.out.println(hex2);
        System.out.println(objectString);

        console printout:

        java.lang.Object -> name
        15db9742 -> hashCode replaced to hexadecimal
        366712642 -> hashCode 
        15db9742 
        java.lang.Object@15db9742 -> method toString();

        EXPLAIN:

        Method Object.hashCode(); -> any object has own unique value,
        this method uses a memory address to calculate int value.
        Method .toString(); uses the same memory address but in hexadecimal convention
            */
        assertEquals(expectedToString, object.toString());
        // hint: object.toString()
    }

    @Koan
    public void toStringConcatenates() {
        final String string = "ha";
        Object object = new Object() {

            //!learn about annotation @Override!

            @Override
            public String toString() {
                return string;
            }
        };
        assertEquals(string + object, "haha");
    }

    @Koan
    public void toStringIsTestedForNullWhenInvokedImplicitly() {
        String string = "string";

        //null could be concatenated to string value (sout(variable + null);), but when you try sout(null); then error will be printed!
        assertEquals(string + null, "stringnull");

    }

    private Class<?>[] getAncestors(Object object) {
        List<Class<?>> ancestors = new ArrayList<Class<?>>();
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            ancestors.add(clazz);
            clazz = clazz.getSuperclass();
        }
        return ancestors.toArray(new Class[]{});
    }

}
