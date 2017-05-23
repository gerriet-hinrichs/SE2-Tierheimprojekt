/*
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <felix.koch@haw-hamburg.de>,
 *     Kristian Exss <Kristian.Exss@HAW-Hamburg.de>,
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
 *     Karsten Boehringer <Karsten.Boehringer@HAW-Hamburg.de>,
 *     Gehui Xu <Gehui.Xu@HAW-Hamburg.de>,
 *     Gerriet Hinrichs <gerriet.hinrichs@haw-hamburg.de>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.stuff42.se2tierheimprojekt.controller.test;

import java.util.List;
import java.util.Map;

import de.stuff42.apigenerator.annotation.GenerateClientApi;
import de.stuff42.se2tierheimprojekt.controller.BaseController;
import de.stuff42.se2tierheimprojekt.entity.FakeEntity;
import de.stuff42.se2tierheimprojekt.service.FakeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@GenerateClientApi
public class TestController extends BaseController<FakeService> {

    /**
     * Creates instance for the given service.
     *
     * @param serviceInstance Service instance.
     */
    @Autowired
    public TestController(FakeService serviceInstance) {
        super(serviceInstance);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Iterable<FakeEntity> getList() {
        return service.getList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test/{text}")
    public FakeEntity add(@PathVariable String text) {
        logger.info(text);
        return service.add(text);
    }

    // Methods to test api generation & type exports
    @RequestMapping(method = RequestMethod.GET, value = "/_byte")
    public byte _byte() {
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Byte_")
    public Byte _Byte_() {
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_short")
    public short _short() {
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Short_")
    public Short _Short_() {
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_int")
    public int _int() {
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Integer_")
    public Integer _Integer_() {
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_float")
    public float _float() {
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Float_")
    public Float _Float_() {
        return 0.f;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_double")
    public double _double() {
        return 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Double_")
    public Double _Double_() {
        return 0.;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_char")
    public char _char() {
        return '\0';
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Char_")
    public double _Char_() {
        return '\0';
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_String_")
    public String _String_() {
        return "";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Object_")
    public Object _Object_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Array_")
    public String[] _Array_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Iterable_")
    public Iterable<Object> _Iterable_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_RawIterable_")
    @SuppressWarnings("rawtypes")
    public Iterable _RawIterable_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_List_")
    public List<Float> _List_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Enum_")
    public TestEnum _Enum_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_Map_")
    public Map<String, TestObjectA> _Map_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_TestObject_")
    public TestObjectB _TestObject_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_GenericObject_")
    public TestObjectC<TestObjectA, Bar<TestObjectA, TestObjectB>> _GenericObject_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_GenericObject2_")
    public TestObjectC<?, ?> _GenericObject2_() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/_GenericObject3_")
    public TestObjectC<? extends Base, Bar<TestObjectA, TestObjectB>> _GenericObject3_() {
        return null;
    }

    // test data types for above methods
    public enum TestEnum {
        FOO, BAR, BAZ
    }

    static class Base implements Foo {

    }

    interface Foo {

    }

    public static class TestObjectA extends Base {

        public TestObjectA a;

        public TestObjectB b;
    }

    public static abstract class TestObjectB extends Bar<TestObjectA, TestObjectB> {

        public TestObjectA c;

        public TestObjectB d;
    }

    public static class Bar<A extends Base & Foo, B> extends Base {

        public A foo;

        public B bar;
    }

    public static class TestObjectC<A extends TestObjectA, B extends Bar<?, ?>> extends Base {

        public A ax;

        public B bx;
    }
}
