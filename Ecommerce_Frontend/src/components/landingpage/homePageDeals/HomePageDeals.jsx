import './HomePageDeals.css';
import NayasaDealImg from '../../../assets/HomePage/HomePageDeals/NAYASA-Homecentre-deal.jpg';

function HomePageDeals() {
    return (
        <div className="home-page-deals">
            <img src={NayasaDealImg} alt="" />
            <div className="heading">
                Starting <span className="rupee-symbol">₹</span>136
            </div>
            <div className="description">
                Storage Containers
            </div>
        </div>
    );
}

export default HomePageDeals;