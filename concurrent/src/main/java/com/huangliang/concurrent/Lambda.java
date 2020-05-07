package com.huangliang.concurrent;

import cn.hutool.core.util.RandomUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lambda {

    //流stream用完一次后就不能用了
    //要多次使用 比如list  可以多次调用list.stream

    public static void main(String[] args) {
        test01();
//        test02();
//        test03();
//        test04();
//        test05();
//        test06();
//        test07();
//        test08();
//        test09();
//        test10();
//        test11();
//        test12();
//        test13();
//        test14();
//        test15();
//        test16();
//        test17();
//        test18();
//        test19();
//        test20();
//        test21();
    }

    public static void test01() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        list.forEach(System.out::print);
        //单个参数括号可以省略(s)
        list.forEach(s -> {
            System.out.println(s);
        });
    }

    public static void test02() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        long count = list.stream().count();
        System.out.println(count);
    }

    public static void test03() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> result = list.stream().filter(s -> s.startsWith("a")).collect(Collectors.toList());
        System.out.println(result);
    }

    public static void test04() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        List<String> result = list.stream().filter(s -> {
            return !s.startsWith("a");
        }).limit(3).collect(Collectors.toList());
        System.out.println(result);
    }

    public static void test05() {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e"));
        List<String> result = list.stream().filter(s -> {
            return !s.startsWith("a");
            //limit和skip可以互换位置 不分先后
        }).limit(3).skip(1).collect(Collectors.toList());
        System.out.println(result);
    }

    public static void test06() {
        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        //将一种流转化为另一种流 String -> Integer
        List<Integer> result = list.stream().filter(s -> !s.startsWith("1"))
                .map(s -> Integer.parseInt(s)).collect(Collectors.toList());
        System.out.println(result);
        list.stream().filter(s -> !s.startsWith("1")).map(s -> Integer.parseInt(s))
                .forEach(System.out::print);
        System.out.println();
    }

    public static void test07() {
        List<String> list = new ArrayList<>(Arrays.asList("2", "1", "6", "4", "3"));
        //升序 asc
        list.stream().filter(s -> !s.startsWith("1")).sorted()
                .map(s -> Integer.parseInt(s)).forEach(System.out::print);
        System.out.println();
        //升序 asc
        list.stream().filter(s -> !s.startsWith("1")).map(s -> Integer.parseInt(s))
                //.sorted((i1, i2) -> i1.compareTo(i2)).forEach(System.out::print);
                .sorted(Comparator.naturalOrder()).forEach(System.out::print);
        System.out.println();
        list.stream().filter(s -> !s.startsWith("1")).map(s -> Integer.parseInt(s))
                //.sorted((i1, i2) -> i2.compareTo(i1)).forEach(System.out::print);
                .sorted(Comparator.reverseOrder()).forEach(System.out::print);
        System.out.println();
    }

    public static void test08() {
        List<Car> list = new ArrayList<Car>(Arrays.asList(new Car(RandomUtil.randomString(6)), new Car(RandomUtil.randomString(6)), new Car(RandomUtil.randomString(6)), new Car(RandomUtil.randomString(6))));
        list.stream().sorted(Comparator.comparing(Car::getName)).forEach(System.out::print);
        //list.stream().sorted((c1, c2) -> c1.getName().compareTo(c2.getName()));
        System.out.println();
    }

    public static void test09() {
        List<String> list = new ArrayList<>(Arrays.asList("3", "2", "2", "3", "6"));
        list.stream().distinct().forEach(System.out::print);
        System.out.println();
        list.stream().map(s -> Integer.parseInt(s)).distinct().forEach(System.out::print);
        System.out.println();
    }

    public static void test10() {
        //必须重写类的hashcode和equals方法哦 不然下面会出现5个
        Stream<Car> carStream = Stream.of(new Car("c1", 1), new Car("c2", 2), new Car("c1", 1), new Car("c1", 2), new Car("c3", 2));
        System.out.println(carStream.distinct().collect(Collectors.toList()));
    }

    public static void test11() {
        List<String> list = new ArrayList<>(Arrays.asList("3", "2", "2", "3", "6"));
        boolean b1 = list.stream().allMatch(s -> Integer.parseInt(s) > 1);//true
        boolean b2 = list.stream().allMatch(s -> Integer.parseInt(s) > 3);//false
        boolean b3 = list.stream().anyMatch(s -> Integer.parseInt(s) > 3);//true
        boolean b4 = list.stream().allMatch(s -> Integer.parseInt(s) > 10);//false
        System.out.println(b1 + " " + b2 + " " + b3 + " " + b4);
    }

    public static void test12() {
        //都返回了列表中的第一个元素
        //findAny()操作，返回的元素是不确定的，对于同一个列表多次调用findAny()有可能会返回不同的值。
        //使用findAny()是为了更高效的性能。
        //如果是数据较少，串行地情况下，一般会返回第一个结果，如果是并行的情况，那就不能确保是第一个
        List<String> list = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        System.out.print(list.stream().findFirst().get() + " ");
        System.out.print(list.stream().findAny().get() + " ");
        System.out.println(list.stream().findAny().get());
    }

    public static void test13() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(3, 5, 6, 2, 5, 8, 1));
        List<String> list2 = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
        System.out.print(list1.stream().max((s1, s2) -> s1.compareTo(s2)).get() + " ");//8
        System.out.print(list1.stream().max(Integer::compareTo).get() + " ");        //8
        System.out.print(list1.stream().max(Comparator.naturalOrder()).get() + " "); //8
        System.out.print(list1.stream().max(Comparator.reverseOrder()).get() + " "); //1
        System.out.print(list2.stream().max(Comparator.naturalOrder()).get() + " "); //5
        System.out.print(list2.stream().max(Comparator.reverseOrder()).get() + " "); //1
        System.out.print(list1.stream().min(Integer::compareTo).get() + " ");        //1
        System.out.print(list1.stream().min(Comparator.naturalOrder()).get() + " "); //1
        System.out.print(list2.stream().min(Comparator.naturalOrder()).get() + " "); //1
        System.out.print(list1.stream().min(Comparator.reverseOrder()).get() + " "); //8
        System.out.print(list2.stream().min(Comparator.reverseOrder()).get() + " "); //5
        System.out.println(list2.stream().min(String::compareTo).get());             //1
    }

    public static void test14() {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 6, 2, 5, 8, 1));
        //初始值为0 赋值给x 然后加list第一个数据 后赋值x 加上list第二个数值y
        int total = list.stream().reduce(0, (a, b) -> a + b);
        System.out.println(total);
        //找出最大值
        total = list.stream().reduce(0, (x, y) -> x > y ? x : y);
        System.out.println(total);
    }

    public static void test15() {
        Stream<Car> carStream = Stream.of(new Car("c1", 11), new Car("c2", 22), new Car("c3", 12), new Car("c4", 21), new Car("c5", 22));
        List<Car> collect = carStream.collect(Collectors.toList());
        int totalAge = collect.stream().map(p -> p.getAge()).reduce(0, (x, y) -> x + y);
        int sum = collect.stream().map(p -> p.getAge()).reduce(0, Integer::sum);
        int maxAge = collect.stream().map(p -> p.getAge()).reduce(0, Integer::max);
        int totalTimes = collect.stream().map(s -> {
            return s.getAge() == 22 ? 1 : 0;
        }).reduce(0, Integer::sum);
        System.out.println(totalAge + " " + sum + " " + maxAge + " " + totalTimes);
    }

    public static void test16() {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 6, 2, 5, 8, 1));
        List<Integer> collect = list.stream().mapToInt(Integer::intValue).boxed().filter(x -> x > 3).collect(Collectors.toList());
        //list.stream().mapToInt((x) -> x.intValue()).boxed().collect(Collectors.toList());
        System.out.println(collect);
    }

    public static void test17() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(3, 5, 6));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(2, 5, 8, 1));
        System.out.println(Stream.concat(list1.stream(), list2.stream()).collect(Collectors.toList()));
    }

    public static void test18() {
        Stream<Car> carStream = Stream.of(new Car("c1", 12), new Car("c2", 23), new Car("c3", 12), new Car("c4", 21), new Car("c5", 22));
        List<Car> list = carStream.collect(Collectors.toList());
        //根据age分组
        Map<Integer, List<Car>> map = list.stream().collect(Collectors.groupingBy(a -> a.getAge()));
        //list.stream().collect(Collectors.groupingBy(Car::getAge));
        System.out.println(map);
        //获取最大的
        Optional<Car> max = list.stream().collect(Collectors.maxBy(Comparator.comparing(Car::getAge)));
        //下面和上面一样作用
//        list.stream().collect(Collectors.maxBy((s1, s2) -> s1.getAge().compareTo(s2.getAge())));
//        list.stream().collect(Collectors.maxBy(Comparator.comparing((s) -> {
//            return s.getAge();
//        })));
        System.out.println(max.get());

        //根据条件分区 true一个区 false一个区
        Map<Boolean, List<Car>> booleanListMap = list.stream().collect(Collectors.partitioningBy(s -> s.getAge() > 20));
        System.out.println(booleanListMap);
    }

    public static void test19() {
        Stream<Car> carStream = Stream.of(new Car("c1", 12), new Car("c2", 23), new Car("c3", 12), new Car("c4", 21), new Car("c5", 22));
        List<Car> list = carStream.collect(Collectors.toList());
        String collectNames = list.stream().map(Car::getName).collect(Collectors.joining("-"));
        System.out.println(collectNames); //c1-c2-c3-c4-c5
    }

    public static void test20() {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 5, 6, 2, 5, 8, 1));
        //并行流
        list.stream().parallel().filter(s -> s.intValue() > 2).forEach(System.out::print);
        System.out.println();
        //并行流安全问题
        //加锁 线程安全的容器vector、synchronizedList stream的box
        //Vector<Integer> vector = new Vector<>();
        List<Integer> syncList = Collections.synchronizedList(new ArrayList<>());
        list.parallelStream().forEach(integer -> {
            syncList.add(integer);
            //vector.add(integer);
        });
        System.out.println(syncList.size());
        //调用collect或者toarray 线程安全
        List<Integer> collect = list.parallelStream().mapToInt(Integer::intValue).boxed().collect(Collectors.toList());
        System.out.println(collect.size());
        List<Integer> collect1 = IntStream.rangeClosed(1, 10000).parallel().boxed().collect(Collectors.toList());
        System.out.println(collect1.size());
        Integer[] collect2 = IntStream.rangeClosed(1, 10000).parallel().boxed().toArray(Integer[]::new);
        //Integer[] collect2 = IntStream.rangeClosed(1, 10000).parallel().boxed().toArray(s -> new Integer[s]);
        System.out.println(collect1.size() == collect2.length);
    }

    public static void test21() {
        List<Car> list = new ArrayList<>();
        list.add(new Car("1", 11, "2019-03-03 20:20:20"));
        list.add(new Car("2", 21, "2021-03-03 20:20:20"));
        list.add(new Car("3", 31, "2020-03-03 20:20:20"));
        list.add(new Car("4", 41, "2020-03-03 20:20:20"));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Function<Car, Long> function = car -> {
            try {
                return sf.parse(car.getDate()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0L;
        };
        //默认是从小到大排序 加上reversed后倒序 大到小排序
        list.stream().sorted(Comparator.comparing(function).reversed().thenComparing(Car::getAge))
                .forEach(System.out::println);
        System.out.println("============================");
        //如果要按照升序排序,则o1 小于o2，返回-1（负数），相等返回0，01大于02返回1（正数）
        //如果要按照降序排序,则o1 小于o2，返回1（正数），相等返回0，01大于02返回-1（负数）
        //升序
        list.stream().sorted(new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                long first = 0;
                long last = 0;
                try {
                    first = sf.parse(o1.getDate()).getTime();
                    last = sf.parse(o2.getDate()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (first > last) {
                    return 1;
                } else if (first == last) {
                    return 0;
                } else {
                    return -1;
                }
            }
        }).forEach(System.out::println);

        //降序
        System.out.println("==================");
        list.stream().sorted(new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                long first = 0;
                long last = 0;
                try {
                    first = sf.parse(o1.getDate()).getTime();
                    last = sf.parse(o2.getDate()).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (first > last) {
                    return -1;
                } else if (first == last) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }).forEach(System.out::println);
    }
}

class Car {
    private String name;
    private Integer age;
    private String date;

    Car() {
    }

    Car(String name) {
        this.name = name;
    }

    Car(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    Car(String name, Integer age, String date) {
        this.name = name;
        this.age = age;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        //return ReflectionToStringBuilder.toString(this);
        return "Car{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(name, car.name) &&
                Objects.equals(age, car.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

}