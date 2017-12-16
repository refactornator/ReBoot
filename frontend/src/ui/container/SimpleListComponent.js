import ListComponent from '../component/ListComponent';
import { connect } from 'react-redux';

export default connect(state => ({ items: ['Hello', 'World!'] }), {})(ListComponent);
