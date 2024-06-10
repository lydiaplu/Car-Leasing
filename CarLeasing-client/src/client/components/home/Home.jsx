import React from 'react'

import MainHeader from '../layout/MainHeader'
import Parallax from '../common/Parallax'
import Parallax2 from "../common/Parallax2"

function Home() {
    return (
        <section>

            <MainHeader />
            <div className='container'>
                <Parallax />
                <Parallax2 />

            </div>

        </section>
    )
}

export default Home