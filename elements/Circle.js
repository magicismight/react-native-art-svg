import React from 'react';
import createReactNativeComponentClass from '../lib/createReactNativeComponentClass';
import Shape from './Shape';
import {CircleAttributes} from '../lib/attributes';
import {pathProps, numberProp} from '../lib/props';
import extractProps from '../lib/extract/extractProps';

export default class extends Shape {
    static displayName = 'Circle';

    static propTypes = {
        ...pathProps,
        cx: numberProp.isRequired,
        cy: numberProp.isRequired,
        r: numberProp.isRequired
    };

    static defaultProps = {
        cx: 0,
        cy: 0,
        r: 0
    };

    render() {
        let props = this.props;
        return <RNSVGCircle
            ref={ele => {this.root = ele;}}
            {...extractProps(props, this)}
            cx={props.cx.toString()}
            cy={props.cy.toString()}
            r={props.r.toString()}
        />;
    }
}

const RNSVGCircle = createReactNativeComponentClass('RNSVGCircle', () => ({
    validAttributes: CircleAttributes,
    uiViewClassName: 'RNSVGCircle'
}));
