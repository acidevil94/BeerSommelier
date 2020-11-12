package com.coluzzi.beersommelier.ui.detail

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.coluzzi.beersommelier.R
import com.coluzzi.beersommelier.databinding.ActivityBeerDetailBinding
import com.coluzzi.beersommelier.model.beer.Beer
import com.coluzzi.beersommelier.model.beer.ingredients.hop.Hop
import com.coluzzi.beersommelier.model.beer.ingredients.malt.Malt
import com.coluzzi.beersommelier.model.beer.method.mashtemp.MashTemp
import com.coluzzi.beersommelier.util.Constants


// values for activity's tables design
private const val TABLE_VERTICAL_MARGIN = 6
private const val TABLE_CELL_BORDER_WIDTH = 4
private const val TABLE_BORDER_WIDTH = 8

/**
 * Activity that has the responsibility to show details
 * of an instance of [Beer]
 */
class BeerDetailActivity : AppCompatActivity() {



    // binding for view
    private lateinit var binding: ActivityBeerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBeerDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // parse beer from extra and show info to view
        parseBeerFromExtra()?.let { it -> showBeerInfo(it)
        }

    }

    private fun parseBeerFromExtra(): Beer? {
        val beerJson = getIntent().getStringExtra(Constants.BEER_KEY)
        return Gson().fromJson(beerJson, Beer::class.java)
    }

    private fun showBeerInfo(beer: Beer) {
        val includedDetailLayout = binding.includedDetailLayout

        includedDetailLayout.beerNameValue.text = beer.name
        includedDetailLayout.beerDescValue.text = beer.description
        includedDetailLayout.beerTagValue.text = beer.tagline
        includedDetailLayout.beerFirstBrewValue.text = beer.firstBrewed
        Picasso.get().load(beer.imageUrl).into(includedDetailLayout.beerImage);
        includedDetailLayout.beerABVDesc.text = beer.abv.toString() + " %"
        includedDetailLayout.beerIBUDesc.text = beer.ibu.toString()
        includedDetailLayout.beerGravityDesc.text = beer.targetOG.toString() + " -> " + beer.targetFG.toString()
        includedDetailLayout.beerEBCDesc.text = beer.ebc.toString()
        includedDetailLayout.beerSRMDesc.text = beer.srm.toString()
        includedDetailLayout.beerPHDesc.text = beer.ph.toString()
        includedDetailLayout.beerAttLevDesc.text = beer.attenuationLevel.toString()
        includedDetailLayout.beerVolumeDesc.text = beer.volume.format()
        includedDetailLayout.beerBoilVolumeDesc.text = beer.boilVolume.format()
        fillMashTemperatures(includedDetailLayout.beerMashTempTable, beer.method.mashTemp)
        includedDetailLayout.beerFermentationTempDesc.text = beer.method.fermentation.temp.format()
        includedDetailLayout.beerTwistDesc.text = beer.method.twist
        fillMalts(includedDetailLayout.beerMaltTable, beer.ingredients.malt)
        fillHops(includedDetailLayout.beerHopTable, beer.ingredients.hops)
        includedDetailLayout.beerYeastDesc.text = beer.ingredients.yeast
        includedDetailLayout.beerFoodPairingDesc.text = beer.foodPairing.joinToString()
        includedDetailLayout.beerTipsDesc.text = beer.brewersTips
    }


    private fun fillHops(beerHopTable: TableLayout, hops: List<Hop>) {
        var rows = mutableListOf(arrayOf(getString(R.string.name), getString(R.string.amount)))
        hops.stream().forEach{
            rows.add(arrayOf(it.name, it.amount.format()))
        }
        setupTable(beerHopTable, rows.toTypedArray() )
    }

    private fun fillMalts(beerMaltTable: TableLayout, malt: List<Malt>) {
        var rows = mutableListOf(arrayOf(getString(R.string.name), getString(R.string.amount)))
        malt.stream().forEach{
            rows.add(arrayOf(it.name, it.amount.format()))
        }
        setupTable(beerMaltTable, rows.toTypedArray() )
    }

    private fun fillMashTemperatures(beerMashTable: TableLayout, mashTemp: List<MashTemp>) {
        var rows = mutableListOf(arrayOf(getString(R.string.temperature), getString(R.string.duration)))
        mashTemp.stream().forEach{
            val durationStr =  if(it.duration != null) it.duration.toString() else ""
            rows.add(arrayOf(it.temp.format(), durationStr))
        }
        setupTable(beerMashTable, rows.toTypedArray() )
    }

    private fun setupTable(table: TableLayout, rows : Array<Array<String>>) {
        var tableRow: TableRow
        var textView: TextView
        table.isStretchAllColumns = true
        table.background = borderDrawable(TABLE_BORDER_WIDTH)
        table.setPadding(TABLE_BORDER_WIDTH, TABLE_BORDER_WIDTH, TABLE_BORDER_WIDTH, TABLE_BORDER_WIDTH)

        var isFirstRow = true

        for (row in rows) {
            tableRow = TableRow(applicationContext)
            for (currentColumn in row) {
                textView = TextView(applicationContext)
                textView.setTextColor(getRowColor(isFirstRow))
                textView.background = borderDrawable(TABLE_CELL_BORDER_WIDTH)
                textView.text = currentColumn
                textView.gravity = Gravity.CENTER
                textView.setPadding(0, TABLE_VERTICAL_MARGIN, 0, TABLE_VERTICAL_MARGIN)
                tableRow.addView(textView)
            }
            table.addView(tableRow)

            isFirstRow = false
        }
    }

    private fun getRowColor(isFirst : Boolean) : Int {
        if(isFirst) {
            return getColor(R.color.colorPrimaryDark)
        } else {
            return Color.BLACK
        }
    }

    private fun borderDrawable(borderWidth: Int): GradientDrawable? {
        val shapeDrawable = GradientDrawable()
        shapeDrawable.setStroke(borderWidth, Color.BLACK)
        return shapeDrawable
    }




}