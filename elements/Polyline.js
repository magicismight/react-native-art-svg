import React, {
    Component,
    PropTypes,
    ART
} from 'react-native';
import Path from './Path';


import strokeFilter from '../lib/strokeFilter';
import fillFilter from '../lib/fillFilter';
class Polyline extends Component{
    static displayName = 'Polyline';
    static propTypes = {
        points: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
        strokeLinecap: PropTypes.oneOf(['butt', 'square', 'round']),
        strokeCap: PropTypes.oneOf(['butt', 'square', 'round']),
        strokeLinejoin: PropTypes.oneOf(['miter', 'bevel', 'round']),
        strokeJoin: PropTypes.oneOf(['miter', 'bevel', 'round'])
    };
    render() {
        let props = this.props;
        let d = 'M' + props.points.trim().replace(/\s+/g, 'L');
        return <Path
            {...props}
            {...strokeFilter(props)}
            fill={fillFilter(props)}
            points={null}
            d={d}
        />;
    }
}
export default Polyline;
