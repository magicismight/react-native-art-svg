import React, {
    Component
} from 'react-native';

import Svg, {
    Defs,
    LinearGradient,
    RadialGradient,
    Stop,
    Ellipse,
    Circle
} from 'react-native-art-svg';

class LinearGradientHorizontal extends Component{
    static title = 'Define an ellipse with a horizontal linear gradient from yellow to red';
    render() {
        return <Svg
            height="150"
            width="300"
        >
            <Defs>
                <LinearGradient id="grad" x1="0" y1="0" x2="170" y2="0">
                    <Stop offset="0%" stopColor="rgb(255,255,0)" stopOpacity="0" />
                    <Stop offset="100%" stopColor="red" stopOpacity="1" />
                </LinearGradient>
            </Defs>
            <Ellipse cx="150" cy="75" rx="85" ry="55" fill="url(#grad)" />
        </Svg>;
    }
}

class LinearGradientVertical  extends Component{
    static title = 'Define an ellipse with a horizontal linear gradient from yellow to red';
    render() {
        return <Svg
            height="150"
            width="300"
        >
            <Defs>
                <LinearGradient id="grad" x1="0" y1="0" x2="0" y2="150">
                    <Stop offset="0%" stopColor="rgb(255,255,0)" stopOpacity="0" />
                    <Stop offset="100%" stopColor="red" stopOpacity="1" />
                </LinearGradient>
            </Defs>
            <Ellipse cx="150" cy="75" rx="85" ry="55" fill="url(#grad)" />
        </Svg>;
    }
}

class RadialGradientExample  extends Component{
    static title = 'Define an ellipse with a radial gradient from yellow to purple';
    render() {
        return <Svg
            height="150"
            width="300"
        >
            <Defs>
                <RadialGradient id="grad" cx="150" cy="75" rx="85" ry="55" fx="150" fy="75">
                    <Stop
                        offset="0%"
                        stopColor="#ff0"
                        stopOpacity="1"
                    />
                    <Stop
                        offset="100%"
                        stopColor="#83a"
                        stopOpacity="1"
                    />
                </RadialGradient>
            </Defs>
            <Ellipse cx="150" cy="75" rx="85" ry="55" fill="url(#grad)" />
        </Svg>;
    }
}

class RadialGradientExample2  extends Component{
    static title = 'Define another ellipse with a radial gradient from white to blue';
    render() {
        return <Svg
            height="150"
            width="300"
        >
            <Defs>
                <RadialGradient id="grad" cx="60" cy="45" rx="34" ry="33" fx="150" fy="75">
                    <Stop
                        offset="0%"
                        stopColor="#fff"
                        stopOpacity="1"
                    />
                    <Stop
                        offset="100%"
                        stopColor="#00f"
                        stopOpacity="1"
                    />
                </RadialGradient>
            </Defs>
            <Ellipse cx="150" cy="75" rx="85" ry="55" fill="url(#grad)" />
        </Svg>;
    }
}

const icon = <Svg
    height="20"
    width="20"
>
    <Defs>
        <LinearGradient id="grad" x1="0" y1="0" x2="0" y2="20">
            <Stop offset="0%" stopColor="rgb(255,255,0)" stopOpacity="0" />
            <Stop offset="100%" stopColor="red" stopOpacity="1" />
        </LinearGradient>
    </Defs>
    <Circle cx="10" cy="10" r="10" fill="url(#grad)" />
</Svg>;


const samples = [LinearGradientHorizontal, LinearGradientVertical, RadialGradientExample, RadialGradientExample2];

export {
    icon,
    samples
};
