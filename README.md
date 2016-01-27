### react-native-art-svg

------------------------

`react-native-art-svg` is built to provide a SVG interface to react native on both iOS and Android which is based on *ART*

#### Features

1. Supports most of SVG elements and properties(Rect, Circle, Line, Polyline, Polygon, G ...).
2. 100% based on ReactNative`s ART library
3. Easy to convert SVG code into ReactNative code.

![example](./screenShoots/art-svg.gif)

#### Install

`npm install react-native-art-svg`

##### On iOS we should add import `ART` library into your project

To add ART.xcodeproj find the file located in react-native/Libraries/ART/ART.xcodeproj and just drag it over to the Libraries section in XCode. That should look like so

![Add ART.xcodeproj to Libraries](./screenShoots/addXcodeproj.png)

Next we’ll link the binary.

With the root project selected on the left, select `Build Phases` from the center view. There will be a section called “Link Binary With Libraries”, expand it, press the + and select `libART.a`

Like so

![Link binary](./screenShoots/linkBinary.png)

([Getting react art running on react native](http://browniefed.com/blog/2015/05/03/getting-react-art-running-on-react-native/))

##### On android

react-native\`s `ART` for android is shipped within react-native@0.18.0

#### Usage

There is an easy example

```
import Svg,{
    Circle,
    Ellipse,
    G,
    LinearGradient,
    RadialGradient,
    Line,
    Path,
    Polygon,
    Polyline,
    Rect,
    Symbol,
    Text,
    Use,
    Defs
} from 'react-native-art-svg';

class SvgExample extends Component {
    render() {
        return (
            <Svg
                height="100"
                width="100"
            >
                <Circle
                    cx="50"
                    cy="50"
                    r="45"
                    stroke="blue"
                    strokeWidth="2.5"
                    fill="green"
                />
                <Rect
                    x="15"
                    y="15"
                    width="70"
                    height="70"
                    stroke="red"
                    strokeWidth="2"
                    fill="yellow"
                />
            </Svg>
        );
    }
}

```

This will draw a graphics like this
![SVG example](./screenShoots/svg.png)

#### Common props:

Name            | Default    | Description
----------------|------------|--------------
fill            | '#000'     | The fill prop refers to the color inside the shape.
stroke          | 'none'     | The stroke prop controls how the outline of a shape appears.
strokeWidth     | 1          | The strokeWidth prop specifies the width of the outline on the current object.
strokeOpacity   | 1          | The strokeOpacity prop specifies the opacity of the outline on the current object.
strokeLinecap   | 'square'   | The strokeLinecap prop specifies the shape to be used at the end of open subpaths when they are stroked.
strokeLinejoin  | 'miter'    | The strokeLinejoin prop specifies the shape to be used at the corners of paths or basic shapes when they are stroked.
strokeDasharray | '[]'       | The strokeDasharray prop controls the pattern of dashes and gaps used to stroke paths.
strokeDashoffset| null       | The strokeDashoffset prop specifies the distance into the dash pattern to start the dash.
x               | 0          | Translate distance on x-axis.
y               | 0          | Translate distance on y-axis.
rotate          | 0          | Rotation degree value on the current object.
scale           | 1          | Scale value on the current object.
origin          | 0, 0       | Transform origin coordinates for the current object.
originX         | 0          | Transform originX coordinates for the current object.
originY         | 0          | Transform originY coordinates for the current object.


#### Supported elements:

- Svg

```
<Svg
    height="100"
    width="100"
>
    <Rect x="0" y="0" width="100" height="100" fill="black" />
    <Circle cx="50" cy="50" r="30" fill="yellow" />
    <Circle cx="40" cy="40" r="4" fill="black" />
    <Circle cx="60" cy="40" r="4" fill="black" />
    <Path d="M 40 60 A 10 10 0 0 0 60 60" stroke="black" />
</Svg>;
```

- Rect

The <Rect> element is used to create a rectangle and variations of a rectangle shape:

```
<Svg
    width="200"
    height="60"
>
    <Rect
        x="25"
        y="5"
        width="150"
        height="50"
        fill="rgb(0,0,255)"
        strokeWidth="3"
        stroke="rgb(0,0,0)"
    />
</Svg>
```

![Rect](./screenShoots/rect.png)

- Circle

The <Circle> element is used to create a circle:

```
<Svg
    height="100"
    width="100"
>
    <Circle
        cx="50"
        cy="50"
        r="50"
        fill="pink"
    />
</Svg>
```

![Rect](./screenShoots/circle.png)

  Code explanation:

  * The cx and cy attributes define the x and y coordinates of the center of the circle. If cx and cy are omitted, the circle's center is set to (0,0)
  * The r attribute defines the radius of the circle

- Ellipse

The <Ellipse> element is used to create an ellipse.

An ellipse is closely related to a circle. The difference is that an ellipse has an x and a y radius that differs from each other, while a circle has equal x and y radius

```
<Svg
    height="100"
    width="110"
>
    <Ellipse
        cx="55"
        cy="55"
        rx="50"
        ry="30"
        stroke="purple"
        strokeWidth="2"
        fill="yellow"
    />
</Svg>
```
![Rect](./screenShoots/ellipse.png)

  Code explanation:

  * The cx attribute defines the x coordinate of the center of the ellipse
  * The cy attribute defines the y coordinate of the center of the ellipse
  * The rx attribute defines the horizontal radius
  * The ry attribute defines the vertical radius

- Line

The <Line /> element is an SVG basic shape, used to create a line connecting two points.

```
<Svg
    height="100"
    width="100"
>
    <Line
        x1="0"
        y1="0"
        x2="100"
        y2="100"
        stroke="red"
        strokeWidth="2"
    />
</Svg>
```

![Rect](./screenShoots/line.png)

  Code explanation:

  * The x1 attribute defines the start of the line on the x-axis
  * The y1 attribute defines the start of the line on the y-axis
  * The x2 attribute defines the end of the line on the x-axis
  * The y2 attribute defines the end of the line on the y-axis

- Polygon

The <Polygon /> element is used to create a graphic that contains at least three sides.
Polygons are made of straight lines, and the shape is "closed" (all the lines connect up).

```
<Svg
    height="100"
    width="100"
>
    <Polygon
        points="40,5 70,80 25,95"
        fill="lime"
        stroke="purple"
        strokeWidth="1"
    />
</Svg>
```

![Rect](./screenShoots/polygon.png)

  Code explanation:

  * The points attribute defines the x and y coordinates for each corner of the polygon

- Polyline

The <Polyline /> element is used to create any shape that consists of only straight lines:

```
<Svg
    height="100"
    width="100"
>
    <Polyline
        points="10,10 20,12 30,20 40,60 60,70 95,90"
        fill="none"
        stroke="black"
        strokeWidth="3"
    />
</Svg>
```

![Rect](./screenShoots/polyline.png)

  Code explanation:

  * The points attribute defines the x and y coordinates for each point of the polyline

- Path

The <path> element is used to define a path.
The following commands are available for path data:

  * M = moveto
  * L = lineto
  * H = horizontal lineto
  * V = vertical lineto
  * C = curveto
  * S = smooth curveto
  * Q = quadratic Bézier curve
  * T = smooth quadratic Bézier curveto
  * A = elliptical Arc
  * Z = closepath
`Note:` All of the commands above can also be expressed with lower letters. Capital letters means absolutely positioned, lower cases means relatively positioned.

```
<Svg
    height="100"
    width="100"
>
    <Path
        d="M25 10 L98 65 L70 25 L16 77 L11 30 L0 4 L90 50 L50 10 L11 22 L77 95 L20 25"
        fill="none"
        stroke="red"
    />
</Svg>
```

![Rect](./screenShoots/path.png)


- Text

The <text> element is used to define a text.

```
<Svg
    height="60"
    width="200"
>
    <Text
        fill="none"
        stroke="purple"
        fontSize="20"
        fontWeight="bold"
        x="100"
        y="20"
        alignment="center"
    >STROKED TEXT</Text>
</Svg>
```

![Rect](./screenShoots/text.png)

#### Run example:

```

cd ./Example
npm install

```

#### TODO:

1. fillRule="evenodd" ([ART do not support fillRule](https://github.com/facebook/react-native/pull/5477))
2. clipPath ([wait for official supports](https://github.com/facebook/react-native/blob/master/Libraries/ART/ARTGroup.m#L16))
3. textPath ([wait for official supports](https://github.com/facebook/react-native/blob/master/Libraries/ART/ARTText.m#L56))
4. pattern ([wait for official supports](https://github.com/facebook/react-native/blob/master/Libraries/ART/ReactNativeART.js#L332))
5. [animations](https://github.com/gorangajic/react-svg-morph)

#### Thanks:

[SVG bounding Algorithm](https://github.com/icons8/svg-path-bounding-box)
[Circle drawing with svg arc path](http://stackoverflow.com/questions/5737975/circle-drawing-with-svgs-arc-path/10477334#10477334)
[w3schools.com SVG Tutorial](http://www.w3schools.com/svg/)
